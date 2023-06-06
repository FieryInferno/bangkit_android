package com.example.bangkitandroid.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardHorizontalItem(
    title: String,
    image: String,
    subTitle: String,
    drawableSubTitleImage: Int,
    modifier: Modifier = Modifier,
    imageDescription: String = "",
    onTap: () -> Unit
    ){
        Box(
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Card(
                modifier = Modifier.height(80.dp).clickable { onTap() },
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(title, fontSize = 14.sp, fontWeight = FontWeight(800), maxLines = 2)
                        Row(

                        ) {
                            Image(
                                painter = painterResource(id = drawableSubTitleImage),
                                contentDescription = imageDescription
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = subTitle, fontSize = 11.sp, maxLines = 1)
                        }
                    }
                }
            }
        }
    }