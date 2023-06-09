package com.example.bangkitandroid.ui.disease

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityDiseaseImagePreviewBinding
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.service.rotateBitmap
import com.example.bangkitandroid.service.saveRotatedImage
import com.example.bangkitandroid.ui.profile.CameraActivity
import com.example.bangkitandroid.ui.profile.EditProfileActivity
import com.example.bangkitandroid.service.Result
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
            intent.getSerializableExtra(EXTRA_PICTURE, File::class.java)
        } else {
            intent.getSerializableExtra(EXTRA_PICTURE)
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

                viewModel.postAnalyzeDisease().observe(this@DiseaseImagePreviewActivity){
                    when(it){
                        is Result.Loading -> {layoutPreviewLoading.layoutLoadingFullScreen.visibility = View.VISIBLE}
                        is Result.Success -> {
                            layoutPreviewLoading.layoutLoadingFullScreen.visibility = View.GONE
                            val intent = Intent(this@DiseaseImagePreviewActivity, DiseaseDetailActivity::class.java)
                            intent.putExtra(DiseaseDetailActivity.EXTRA_DISEASE, it.data)
                            intent.putExtra(DiseaseDetailActivity.EXTRA_PICTURE, viewModel.getFile().value)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error -> {
                            layoutPreviewLoading.layoutLoadingFullScreen.visibility = View.GONE
                            showToast(this@DiseaseImagePreviewActivity, getString(R.string.analysis_failed))
                        }
                    }
                }

            }
            btnRetakeImage.setOnClickListener {
                startCameraX()
            }
        }
    }

    private fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(this@DiseaseImagePreviewActivity, CameraActivity::class.java))
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == EditProfileActivity.CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra(EXTRA_PICTURE, File::class.java)
            } else {
                it.data?.getSerializableExtra(EXTRA_PICTURE)
            } as File?
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            if(myFile != null){
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )
                viewModel.setFile(saveRotatedImage(result, myFile))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_PICTURE = "extra_picture"
    }
}