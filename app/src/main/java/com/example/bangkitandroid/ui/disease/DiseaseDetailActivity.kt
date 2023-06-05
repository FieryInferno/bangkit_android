package com.example.bangkitandroid.ui.disease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityDiseaseDetailBinding
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData

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
        disease = DummyData().getDetailDisease(0)
    }

    private fun setView(){
        val adapter = ProductRecommendationAdapter(disease.products)
        binding?.apply {
            tvDiseaseName.text = disease.title
            tvDiseaseDescription.text = disease.description
            tvDiseaseDateTime.text = disease.createdAt
            tvDiseaseTreatment.text = disease.treatment
            Glide.with(this@DiseaseDetailActivity)
                .load("https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg")
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
}