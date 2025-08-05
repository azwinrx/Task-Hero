package com.azwin.dotask.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun timerLogic() {

    // Countdown Timer in Second
    var ticks by remember {mutableStateOf(900)}
    LaunchedEffect(Unit) {
        while (ticks > 0 ) {
            delay(1.seconds)
            ticks--
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center, // Centers children horizontally
        verticalAlignment = Alignment.CenterVertically

    ){
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.75f)
        ){
                Text("$ticks Seconds More", modifier = Modifier.padding())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
            )
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun timerLogicPreview() {
    timerLogic(
    )
}