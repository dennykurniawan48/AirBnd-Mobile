package com.dennydev.airbnd.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.dennydev.airbnd.model.common.Constant
import com.dennydev.airbnd.model.response.categories.Categories
import com.dennydev.airbnd.model.response.categories.Data

@Composable
fun ListCategory(
    data: Categories,
    onCategoryClicked: (String) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        LazyRow(modifier=Modifier.fillMaxWidth()){
            items(data.data){
                Card(elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),modifier= Modifier
                    .width(80.dp)
                    .padding(4.dp)
                    .clickable {
                        onCategoryClicked(it.url_slug)
                    }) {
                    Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context = context)
                                .data("${Constant.BASE_URL}${it.icon}")
                                .decoderFactory(SvgDecoder.Factory())
                                .build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = "Image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .width(60.dp)
                                .padding(8.dp)
                                .aspectRatio(1f)
                        )
                        Column(modifier= Modifier
                            .fillMaxWidth()
                            .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = it.name, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}