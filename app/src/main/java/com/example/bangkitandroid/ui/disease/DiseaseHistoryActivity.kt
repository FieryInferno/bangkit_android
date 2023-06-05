package com.example.bangkitandroid.ui.disease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bangkitandroid.R
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
//        val adapter = DiseaseHistoryAdapter()
//        adapter.setOnItemTapCallback(object : DiseaseHistoryAdapter.OnItemTapCallback{
//            override fun onItemTap(data: Disease) {
//                val intent = Intent(this@DiseaseHistoryActivity, DiseaseDetailActivity::class.java)
//                startActivity(intent)
//            }
//        })
//        binding?.apply {
//            rvDiseaseHistory.layoutManager = LinearLayoutManager(this@DiseaseHistoryActivity)
//            rvDiseaseHistory.adapter = adapter
//            btnDiseaseHistoryBack.setOnClickListener {
//                finish()
//            }
//        }
        setContent {
            MaterialTheme() {
                LazyColumn{
                    items(diseases.size, key = {diseases[it].id}){
                        CardHorizontalItem(
                            title = diseases[it].title,
                            image = diseases[it].image,
                            subTitle = diseases[it].description)
                    }
                }
            }
        }
    }
    
    @Composable
    fun CardHorizontalItem(
        title: String,
        image: String,
        subTitle: String,
        modifier: Modifier = Modifier
    ){
        Card(
            modifier = Modifier.height(80.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_leaf),
                    contentDescription = "icon",
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                    )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ){
                    Text(title, fontSize = 14.sp, fontWeight = FontWeight(800), maxLines = 2)
                    Row(

                    ){
                        Image(painter = painterResource(id = R.drawable.baseline_access_time_16), contentDescription = "time icon")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = subTitle, fontSize = 11.sp, maxLines = 1)
                    }
                }
            }
        }
    }
    
    @Composable
    @Preview
    fun Preview(){
        diseases = DummyData().getHistoryDiseases()
        MaterialTheme() {
            Scaffold(
                backgroundColor = Color(0xFFE7F0EB)
            ) { innerPadding ->
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 8.dp),
                    modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)
                ){
                    items(diseases.size, key = {diseases[it].id}){
                        CardHorizontalItem(
                            title = diseases[it].title,
                            image = diseases[it].image,
                            subTitle = diseases[it].description)
                    }
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}