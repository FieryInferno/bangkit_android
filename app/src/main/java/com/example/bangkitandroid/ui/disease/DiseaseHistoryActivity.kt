package com.example.bangkitandroid.ui.disease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.databinding.ActivityDiseaseHistoryBinding
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData

class DiseaseHistoryActivity : AppCompatActivity() {
    private lateinit var diseases: List<Disease>
    private var binding: ActivityDiseaseHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        getData()
        setView()
    }

    private fun getData(){
        diseases = DummyData().getHistoryDiseases()
    }

    private fun setView(){
        val adapter = DiseaseHistoryAdapter()
        adapter.setOnItemTapCallback(object : DiseaseHistoryAdapter.OnItemTapCallback{
            override fun onItemTap(data: Disease) {
                val intent = Intent(this@DiseaseHistoryActivity, DiseaseDetailActivity::class.java)
                startActivity(intent)
            }
        })
        binding?.apply {
            rvDiseaseHistory.layoutManager = LinearLayoutManager(this@DiseaseHistoryActivity)
            rvDiseaseHistory.adapter = adapter
            btnDiseaseHistoryBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}