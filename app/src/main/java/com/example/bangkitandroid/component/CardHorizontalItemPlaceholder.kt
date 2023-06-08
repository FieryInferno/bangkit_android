package com.example.bangkitandroid.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardHorizontalItemPlaceholder(
    modifier: Modifier = Modifier,
    ){
        Box(
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(80.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier
                        .background(color = AppColors.green200)
                        .height(64.dp)
                        .width(64.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column {
                            Box(modifier = Modifier
                                .background(color = AppColors.green200)
                                .height(14.dp)
                                .fillMaxWidth())
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(modifier = Modifier
                                .background(color = AppColors.green200)
                                .height(14.dp)
                                .fillMaxWidth())
                        }
                        Box(modifier = Modifier
                            .background(color = AppColors.green200)
                            .height(14.dp)
                            .fillMaxWidth())

                    }
                }
            }
        }
    }