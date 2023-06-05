package com.example.bangkitandroid.component

@Composable
    fun CardHorizontalItem(
        title: String,
        image: String,
        subTitle: String,
        modifier: Modifier = Modifier,
        imageDescription: String = "",
    ){
        Box(
            modifier = modifier.padding(bottom = 16.dp)
        ) {
            Card(
                modifier = Modifier.height(80.dp),
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
                                painter = painterResource(id = R.drawable.baseline_access_time_16),
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