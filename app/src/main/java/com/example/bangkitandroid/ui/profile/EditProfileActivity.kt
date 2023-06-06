package com.example.bangkitandroid.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityEditProfileBinding
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.service.rotateBitmap
import com.example.bangkitandroid.service.saveRotatedImage
import com.example.bangkitandroid.service.uriToFile
import com.example.bangkitandroid.service.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityEditProfileBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Failed getting permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
    }

    private fun setupView() {
        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        binding.apply {
            if (user?.imgUrl != "") {
                Glide.with(this@EditProfileActivity).load(user?.imgUrl).into(editPhoto)
            } else {
                editPhoto.setImageResource(R.drawable.image_profile_default)
            }

            editPhoto.setOnClickListener {
                showPopup()

                if (!allPermissionsGranted()) {
                    ActivityCompat.requestPermissions(
                        this@EditProfileActivity,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                }
            }

            editName.hint = user?.name
            editPhone.hint = user?.phoneNumber

            saveButton.setOnClickListener {
                val newName = editName.text.toString()
                val newPhone = editPhone.text.toString()

                val updatedName = newName.ifEmpty { user?.name }
                val updatedPhone = newPhone.ifEmpty { user?.phoneNumber }

                if (getFile != null) {
                    val nameBody = updatedName?.toRequestBody("text/plain".toMediaType())
                    val phoneBody = updatedPhone?.toRequestBody("text/plain".toMediaType())
                    val requestImageFile = getFile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "image",
                        getFile!!.name,
                        requestImageFile
                    )

                    viewModel.editProfile(nameBody!!, phoneBody!!, imageMultipart).observe(this@EditProfileActivity) {
                        if (it != null) {
                            when (it) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    finish()
                                    startActivity(Intent(this@EditProfileActivity, ProfileLoggedActivity::class.java))
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Snackbar.make(
                                        window.decorView.rootView,
                                        it.error,
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                } else {
                    val nameBody = updatedName?.toRequestBody("text/plain".toMediaType())
                    val phoneBody = updatedPhone?.toRequestBody("text/plain".toMediaType())

                    viewModel.editProfile(nameBody!!, phoneBody!!, null).observe(this@EditProfileActivity) {
                        if (it != null) {
                            when (it) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    finish()
                                    startActivity(Intent(this@EditProfileActivity, ProfileLoggedActivity::class.java))
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Snackbar.make(
                                        window.decorView.rootView,
                                        it.error,
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun showPopup() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.popup_photo_picker)

        val photoButton = dialog.findViewById<TextView>(R.id.photo_button)
        val galleryButton = dialog.findViewById<TextView>(R.id.gallery_button)

        photoButton?.setOnClickListener { startTakePhoto() }
        galleryButton?.setOnClickListener { startGallery() }

        dialog.show()
    }

    private var getFile: File? = null
    @Suppress("DEPRECATION")
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            getFile = saveRotatedImage(result, myFile)

            binding.editPhoto.setImageBitmap(result)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile

            binding.editPhoto.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "USER"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}