package com.example.bangkitandroid.ui.disease

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityDiseaseDetailBinding
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DateFormatter

class DiseaseDetailActivity : AppCompatActivity() {
    private lateinit var disease: Disease
    private var binding: ActivityDiseaseDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        getData()
        setView()
    }

    private fun getData(){
        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DISEASE, Disease::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DISEASE)
        } as Disease
        disease = data
    }

    private fun setView(){
        val adapter = ProductRecommendationAdapter(disease.products)
        binding?.apply {
            tvDiseaseName.text = disease.title
            tvDiseaseDescription.text = disease.description
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                tvDiseaseDateTime.text = DateFormatter.formatDate(disease.createdAt)
            }

            tvDiseaseTreatment.text = disease.treatment
            Glide.with(this@DiseaseDetailActivity)
                .load(disease.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgDiseaseDetail)
            rvDiseaseProductRecommendation.layoutManager = LinearLayoutManager(this@DiseaseDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvDiseaseProductRecommendation.adapter = adapter
            btnDiseaseBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_DISEASE = "extra_disease"
    }
}