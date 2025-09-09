package com.azwin.dotask.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azwin.dotask.ui.theme.jersey25

@Composable
fun StatisticBar(progress: Float, color: Color, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(20.dp)
            .border(width = 1.5.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress)
                .fillMaxHeight()
                .background(color = color, shape = RoundedCornerShape(10.dp)),
        )
        Text(
            fontFamily = jersey25,
            modifier = Modifier.fillMaxWidth(),
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}