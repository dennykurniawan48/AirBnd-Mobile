package com.dennydev.airbnd.component.product

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/*
*
*  Inspired from : https://github.com/mazzouzi/collapsing-toolbar-with-parallax-effect-and-curved-motion-in-compose
*
 */

@Composable
fun Header(
    image: String,
    scrollState: ScrollState,
    screenWidthPx: Float,
    context: Context
) {
    Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).graphicsLayer {
        translationY = -scrollState.value.toFloat() / 2f
        alpha = (-1f / screenWidthPx) * scrollState.value + 1
    }) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context = context)
                .data(image)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                        startY = 3 * screenWidthPx / 4 // to wrap the title only
                    )
                )
        )
    }
}