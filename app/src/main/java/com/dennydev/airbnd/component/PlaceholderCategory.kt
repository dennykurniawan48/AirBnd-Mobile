package com.dennydev.airbnd.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dennydev.airbnd.model.response.categories.Categories

@Composable
fun PlaceholderCategory() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        LazyRow(modifier= Modifier.fillMaxWidth()){
            items(6){
                Card(elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),modifier= Modifier
                    .width(60.dp)
                    .padding(4.dp)
                    .clickable {
                    }) {
                    Column(modifier= Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(brush = shimmerEffect())
                        )
                        Column(modifier= Modifier
                            .fillMaxWidth()
                            .padding(6.dp)) {
                            Box(modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                                .background(brush = shimmerEffect()))
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}