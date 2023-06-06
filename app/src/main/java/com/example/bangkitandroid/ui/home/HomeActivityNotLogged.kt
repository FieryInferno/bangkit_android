package com.example.bangkitandroid.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeNotLoggedBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.mapper.toListBlog
import com.example.bangkitandroid.service.*
import com.example.bangkitandroid.ui.disease.DiseaseImagePreviewActivity
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import com.google.android.material.snackbar.Snackbar
import java.io.File

class HomeActivityNotLogged : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeNotLoggedBinding
    private lateinit var blogs: List<Blog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNotLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupBottomNavigationView()


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        viewModel.getFile().observe(this@HomeActivityNotLogged){
            if(it != null){
                binding.apply {
                    homePopupPhotoPicker.root.visibility = View.GONE
                    homePopupPhotoPickerModal.visibility = View.GONE
                    bottomNavigation.visibility = View.VISIBLE
                }
                val intent = Intent(this, DiseaseImagePreviewActivity::class.java)
                intent.putExtra(DiseaseImagePreviewActivity.EXTRA_PICTURE, it)
                startActivity(intent)
            }
        }
    }

    private fun setupView() {
        binding.emptyHistory.setOnClickListener {
            // intent to login
        }

        viewModel.getHome("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg1OTM1MTM2LCJpYXQiOjE2ODU4OTE5MzYsImp0aSI6IjNiMTJjZDA5Mzk5NDQ1MjJiMTliNDhlNThmZGIwZTY5IiwidXNlcl9pZCI6OH0.qUagcVoeIQYEVuZHtcqfwcxtVp90smT2NYRfY6jkikc").observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        blogs = it.data.blogs.toListBlog()

                        val blogAdapter = BlogAdapter(blogs)
                        blogAdapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
                            override fun onItemTap(data: Blog) {
                                // intent to blog detail
                            }
                        })

                        binding.apply {
                            blogRv.layoutManager = LinearLayoutManager(this@HomeActivityNotLogged)
                            blogRv.adapter = blogAdapter
                            btnScanImage.setOnClickListener{
                                homePopupPhotoPicker.root.visibility = View.VISIBLE
                                homePopupPhotoPickerModal.visibility = View.VISIBLE
                                bottomNavigation.visibility = View.GONE
                            }
                            popupClose.root.setOnClickListener {
                                homePopupPhotoPicker.root.visibility = View.GONE
                                homePopupPhotoPickerModal.visibility = View.GONE
                                bottomNavigation.visibility = View.VISIBLE
                            }
                            homePopupPhotoPicker.photoButton.setOnClickListener {
                                startCameraX()
                            }
                            homePopupPhotoPicker.galleryButton.setOnClickListener {
                                startGallery()
                            }
                        }
                    }
                    is Result.Error -> {
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

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    false
                }
                R.id.blog -> {
                    // Intent to blog page
                    finish()
                    true
                }
                R.id.profile -> {
                    // Intent to profile page not logged
                    finish()
                    true
                }
                else -> false
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(this@HomeActivityNotLogged, CameraActivity::class.java))
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == EditProfileActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                it.data?.getSerializableExtra("picture")
            } as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            viewModel.setFile(saveRotatedImage(result, myFile))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@HomeActivityNotLogged)
                viewModel.setFile(myFile)
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}