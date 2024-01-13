package com.dennydev.airbnd.component.product

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.dennydev.airbnd.model.response.property.Data

/*
*
*  Inspired from : https://github.com/mazzouzi/collapsing-toolbar-with-parallax-effect-and-curved-motion-in-compose
*
 */

@Composable
fun Title(
    scrollState: ScrollState,
          headerHeightPx: Float,
          toolbarHeightPx: Float,
          headerHeight: Dp,
          toolbarHeight: Dp = 56.dp,
    data: Data
    ) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }
    val paddingMedium = 16.dp
    val titlePaddingStart =16.dp
    val titlePaddingEnd = 72.dp
    Text(
        text = data.property.name,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scrollState.value / collapseRange).coerceIn(0f, 1f)

                val titleY = lerp(
                    headerHeight - titleHeightDp - paddingMedium, // start Y
                    toolbarHeight / 2 - titleHeightDp / 2, // end Y
                    collapseFraction
                )

                val titleX = lerp(
                    titlePaddingStart, // start X
                    titlePaddingEnd, // end X
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
            }
            .onGloballyPositioned {
                // We don't know title height in advance to calculate the lerp
                // so we wait for initial composition
                titleHeightPx = it.size.height.toFloat()
            }
    )
}