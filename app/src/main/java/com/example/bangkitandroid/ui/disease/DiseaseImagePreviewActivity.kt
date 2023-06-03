package com.example.bangkitandroid.ui.disease

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.bangkitandroid.databinding.ActivityDiseaseImagePreviewBinding
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.service.rotateBitmap
import com.example.bangkitandroid.service.saveRotatedImage
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import kotlinx.coroutines.*
import java.io.File

class DiseaseImagePreviewActivity : AppCompatActivity() {
    private var binding: ActivityDiseaseImagePreviewBinding? = null
    private val viewModel : DiseaseViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("picture", File::class.java)
        } else {
            intent.getSerializableExtra("picture")
        } as File
        viewModel.setFile(myFile)

        supportActionBar?.hide()
        setView()
        viewModel.getFile().observe(this@DiseaseImagePreviewActivity){
            if(it != null){
                binding?.imgUploadPreview?.setImageBitmap(BitmapFactory.decodeFile(viewModel.getFile().value!!.path))
            }
        }
    }

    private fun setView(){
        binding?.apply {

            btnUploadImage.setOnClickListener {
                layoutPreviewLoading.layoutLoadingFullScreen.visibility = View.VISIBLE
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {

                    delay(3000) // Delay for 2 seconds
                    layoutPreviewLoading.layoutLoadingFullScreen.visibility = View.GONE
                    delay(500) // Delay for 2 seconds
                    val intent = Intent(this@DiseaseImagePreviewActivity, DiseaseHistoryActivity::class.java)
                    finish()
                    startActivity(intent)
                }

            }
            btnRetakeImage.setOnClickListener {
                startCameraX()
            }
            btnDiseasePreviewBack.btnImageBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(this@DiseaseImagePreviewActivity, CameraActivity::class.java))
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}