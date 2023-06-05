package com.example.bangkitandroid.ui.disease

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bangkitandroid.R
import com.example.bangkitandroid.component.CardHorizontalItem
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.service.DateFormatter
import com.example.bangkitandroid.service.ViewModelFactory

class DiseaseHistoryActivity : AppCompatActivity() {

    private val viewModel : DiseaseViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        setContent {
            val diseases = viewModel.getHistoryDisease().collectAsLazyPagingItems()

            DiseaseHistoryActivityApp(diseases)
        }
    }

    @Composable
    fun DiseaseHistoryActivityApp(
        diseases: LazyPagingItems<History>
    ){
        MaterialTheme {
            Scaffold(
                backgroundColor = Color(0xFFE7F0EB),
                topBar = {
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 16.dp)
                            .padding(horizontal = 16.dp)

                    ) {
                        Image(
                            modifier = Modifier.clickable {
                                finish()
                            },
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = getString(R.string.back_button)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = getString(R.string.riwayat), fontSize = 24.sp, fontWeight = FontWeight(800))
                    }
                }
            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                ){
                    items(diseases.itemCount, key = { diseases[it]?.id ?: 0 }){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            CardHorizontalItem(
                                onTap = {
                                    val intent = Intent(this@DiseaseHistoryActivity, DiseaseDetailActivity::class.java)
                                    intent.putExtra("disease", diseases[it]!!.disease)
                                    startActivity(intent)
                                },
                                title = diseases[it]?.disease?.title ?: "Nama Penyakit",
                                image = diseases[it]?.image ?: "-",
                                drawableSubTitleImage = R.drawable.baseline_access_time_16 ,
                                subTitle = DateFormatter.formatDate(diseases[it]?.timestamp ?: "Deskripsi Penyakit"))
                        }
                    }

                    when (diseases.loadState.refresh) { // Pagination
                        is LoadState.Error -> {

                        }
                        is LoadState.Loading -> { // Pagination Loading UI
                            item {
                                Loading()
                            }
                        }
                        else -> {}
                    }
                    when (diseases.loadState.append) { // Pagination
                        is LoadState.Error -> {

                        }
                        is LoadState.Loading -> { // Pagination Loading UI
                            item {
                                Loading()
                            }
                        }
                        else -> {}
                    }
                }
            }

        }
    }

    @Composable
    fun Loading(){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(color = Color.Green)
        }
    }
}