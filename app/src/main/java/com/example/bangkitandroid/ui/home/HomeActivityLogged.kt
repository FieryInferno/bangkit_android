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
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeLoggedBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.mapper.toListBlog
import com.example.bangkitandroid.domain.mapper.toListHistory
import com.example.bangkitandroid.service.*
import com.example.bangkitandroid.ui.blog.BlogDetailActivity
import com.example.bangkitandroid.ui.blog.BlogListActivity
import com.example.bangkitandroid.ui.disease.DiseaseDetailActivity
import com.example.bangkitandroid.ui.disease.DiseaseHistoryActivity
import com.example.bangkitandroid.ui.disease.DiseaseImagePreviewActivity
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import com.example.bangkitandroid.ui.profile.ProfileLoggedActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.io.File

class HomeActivityLogged : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeLoggedBinding
    private var histories: List<History> = emptyList()
    private var blogs: List<Blog> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeLoggedBinding.inflate(layoutInflater)
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

        viewModel.getFile().observe(this@HomeActivityLogged){
            if(it != null){
                binding.apply {
                    bottomNavigation.visibility = View.VISIBLE
                }
                val intent = Intent(this, DiseaseImagePreviewActivity::class.java)
                intent.putExtra(DiseaseImagePreviewActivity.EXTRA_PICTURE, it)
                startActivity(intent)
            }
        }
    }

    private fun setupView() {
        viewModel.getHome().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.apply {
                            seeAllTv.visibility = View.GONE

                            val historyPlaceholderAdapter = HistoryPlaceholderAdapter()
                            historyRv.layoutManager = LinearLayoutManager(
                                this@HomeActivityLogged,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            historyRv.adapter = historyPlaceholderAdapter

                            val blogPlaceholderAdapter = BlogPlaceholderAdapter()
                            blogRv.layoutManager = LinearLayoutManager(this@HomeActivityLogged)
                            blogRv.adapter = blogPlaceholderAdapter
                        }
                    }
                    is Result.Success -> {
                        histories = it.data.history.toListHistory()
                        blogs = it.data.blogs.toListBlog()

                        val historyAdapter = HistoryAdapter(histories)
                        historyAdapter.setOnItemTapCallback(object : HistoryAdapter.OnItemTapCallback{
                            override fun onItemTap(data: History) {
                                val intent = Intent(this@HomeActivityLogged, DiseaseDetailActivity::class.java)
                                intent.putExtra(DiseaseDetailActivity.EXTRA_DISEASE, data.disease)
                                startActivity(intent)
                            }
                        })

                        val blogAdapter = BlogAdapter(blogs)
                        blogAdapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
                            override fun onItemTap(data: Blog) {
                                val intent = Intent(this@HomeActivityLogged, BlogDetailActivity::class.java)
                                intent.putExtra(BlogDetailActivity.EXTRA_BLOG, data.id.toString())
                                startActivity(intent)
                            }
                        })

                        binding.apply {
                            historyRv.layoutManager = LinearLayoutManager(
                                this@HomeActivityLogged,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            historyRv.adapter = historyAdapter

                            blogRv.layoutManager = LinearLayoutManager(this@HomeActivityLogged)
                            blogRv.adapter = blogAdapter

                            if (histories.isEmpty()) {
                                seeAllTv.visibility = View.GONE
                            } else {
                                seeAllTv.visibility = View.VISIBLE
                                seeAllTv.setOnClickListener {
                                    startActivity(Intent(this@HomeActivityLogged, DiseaseHistoryActivity::class.java))
                                }
                            }
                            btnScanImage.setOnClickListener{
                                showPopup()
                            }
                        }
                    }
                    is Result.Error -> {
                        if(it.error.contains("timeout")){
                            viewModel.getHome()
                        } else if(it.error.contains("401")){
                            viewModel.setToken("", "")
                            startActivity(Intent(this@HomeActivityLogged, HomeActivityNotLogged::class.java))
                            finish()
                            Snackbar.make(
                                window.decorView.rootView,
                                "Silakan login kembali",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
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

    private fun showPopup() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.popup_photo_picker)

        val photoButton = dialog.findViewById<TextView>(R.id.photo_button)
        val galleryButton = dialog.findViewById<TextView>(R.id.gallery_button)

        photoButton?.setOnClickListener { startCameraX() }
        galleryButton?.setOnClickListener { startGallery() }

        dialog.show()
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
                    startActivity(Intent(this, ProfileLoggedActivity::class.java))
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
        launcherIntentCameraX.launch(Intent(this@HomeActivityLogged, CameraActivity::class.java))
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
                val myFile = uriToFile(uri, this@HomeActivityLogged)
                viewModel.setFile(myFile)
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}