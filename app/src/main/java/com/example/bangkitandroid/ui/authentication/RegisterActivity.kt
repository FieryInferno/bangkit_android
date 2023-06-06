package com.example.bangkitandroid.ui.authentication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bangkitandroid.R
import com.example.bangkitandroid.component.NameEditText
import com.example.bangkitandroid.component.PasswordEditText
import com.example.bangkitandroid.component.PhoneNumberEditText
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.databinding.ActivityDiseaseHistoryBinding
import com.example.bangkitandroid.databinding.ActivityLoginBinding
import com.example.bangkitandroid.databinding.ActivityRegisterBinding
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.service.rotateBitmap
import com.example.bangkitandroid.service.uriToFile
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {
    private var getFile: File? = null
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthenticationViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var nameEditText: NameEditText
    private lateinit var phoneNumberEditText: PhoneNumberEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var registerButton: Button

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
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this@RegisterActivity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setView()
    }

    private fun setView(){
        binding.imgProfile.setImageResource(R.drawable.image_profile_default)
        // Mendapatkan objek Drawable dari gambar XML
        val drawable = ContextCompat.getDrawable(this, R.drawable.image_profile_default)

        // Mengubah Drawable menjadi Bitmap
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Menyimpan Bitmap sebagai file
        getFile = saveBitmapToFile(bitmap, "image.jpg")

        binding.apply {
            imgProfile.setOnClickListener {
                registerPopupPhotoPicker.root.visibility = View.VISIBLE
                registerPopupPhotoPickerModal.visibility = View.VISIBLE
                btnDaftar.visibility = View.GONE
            }
            popupClose.root.setOnClickListener {
                registerPopupPhotoPicker.root.visibility = View.GONE
                registerPopupPhotoPickerModal.visibility = View.GONE
                btnDaftar.visibility = View.VISIBLE
            }
            registerPopupPhotoPicker.photoButton.setOnClickListener { startTakePhoto() }
            registerPopupPhotoPicker.galleryButton.setOnClickListener { startGallery() }
        }

        nameEditText = binding.etNama
        phoneNumberEditText = binding.etNomorTelepon
        passwordEditText = binding.etPassword
        registerButton = binding.btnDaftar

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setEnableRegisterButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        nameEditText.addTextChangedListener(textWatcher)
        phoneNumberEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        registerButton.setOnClickListener {
            if (getFile != null){
                val file = reduceFileImage(getFile as File)
                val name = nameEditText.text.toString()
                val phoneNumber = phoneNumberEditText.text.toString()
                val password = passwordEditText.text.toString()

                val nameBody = name.toRequestBody("text/plain".toMediaType())
                val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaType())
                val passwordBody = password.toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody()
                val image: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestImageFile
                    )

                viewModel.register(nameBody, phoneNumberBody, passwordBody, image).observe(this) {result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val data = result.data
                                Log.e("data", data.toString())
                                val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(loginIntent)
                                finish()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                val errorMessage = result.error
                                Toast.makeText(this, "$errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }


        binding.apply {
            btnRegisterBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setEnableRegisterButton() {
        val name = nameEditText.text
        val phoneNumber = phoneNumberEditText.text
        val password = passwordEditText.text
        binding.btnDaftar.isEnabled =
                nameEditText.error == null &&
                phoneNumberEditText.error == null &&
                passwordEditText.error == null &&
                name != null &&
                phoneNumber != null &&
                password != null &&
                name.toString().isNotEmpty() &&
                phoneNumber.toString().isNotEmpty() &&
                password.toString().isNotEmpty()
    }

    private fun saveBitmapToFile(bitmap: Bitmap, fileName: String): File? {
        val file = File(cacheDir, fileName)
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @Suppress("DEPRECATION")
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == EditProfileActivity.CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            getFile = saveRotatedImage(result, myFile)

            binding.imgProfile.setImageBitmap(result)
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

            binding.imgProfile.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun saveRotatedImage(bitmap: Bitmap, file: File): File {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
    companion object {
        const val EXTRA_USER = "USER"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}