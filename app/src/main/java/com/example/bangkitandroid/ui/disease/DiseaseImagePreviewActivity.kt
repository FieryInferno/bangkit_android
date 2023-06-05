package com.example.bangkitandroid.ui.disease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityDiseaseImagePreviewBinding
import kotlinx.coroutines.*

class DiseaseImagePreviewActivity : AppCompatActivity() {
    private var binding: ActivityDiseaseImagePreviewBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        binding?.apply {
            Glide.with(this@DiseaseImagePreviewActivity)
                .load("https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgUploadPreview)


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
            btnDiseasePreviewBack.btnImageBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}