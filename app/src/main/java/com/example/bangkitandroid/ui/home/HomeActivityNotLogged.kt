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
import com.example.bangkitandroid.ui.blog.BlogDetailActivity
import com.example.bangkitandroid.ui.blog.BlogListActivity
import com.example.bangkitandroid.ui.disease.DiseaseImagePreviewActivity
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import com.example.bangkitandroid.ui.profile.ProfileNotLoggedActivity
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
            startActivity(Intent(this, ProfileNotLoggedActivity::class.java))
            finish()
        }

        viewModel.getHome().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        showLoading(true)
                        binding.apply {
                            blogTv.visibility = View.GONE
                        }
                    }
                    is Result.Success -> {
                        showLoading(false)

                        blogs = it.data.blogs.toListBlog()

                        val blogAdapter = BlogAdapter(blogs)
                        blogAdapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
                            override fun onItemTap(data: Blog) {
                                val intent = Intent(this@HomeActivityNotLogged, BlogDetailActivity::class.java)
                                intent.putExtra(BlogDetailActivity.EXTRA_BLOG, data.id.toString())
                                startActivity(intent)
                            }
                        })

                        binding.apply {
                            blogTv.visibility = View.VISIBLE

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

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    false
                }
                R.id.blog -> {
                    startActivity(Intent(this, BlogListActivity::class.java))
                    finish()
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileNotLoggedActivity::class.java))
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

    @Suppress("DEPRECATION")
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}