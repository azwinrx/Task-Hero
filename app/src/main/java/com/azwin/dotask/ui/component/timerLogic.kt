package com.azwin.dotask.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun timerLogic() {
    
    var level by remember { mutableStateOf(1) }
    var exp by remember { mutableStateOf(0) }
    var maxExp = 100

    var ticks by remember { mutableStateOf(10) }
    val maxTicks = 10

    var isTimerRunning by remember { mutableStateOf(false) }

    val expBarProgress = exp.toFloat() / maxExp
    val hpBarProgress = ticks.toFloat() / maxTicks

    LaunchedEffect(key1 = isTimerRunning) {
        if (isTimerRunning) {

            while (ticks > 0) {
                delay(1.seconds)
                ticks--
            }
            exp += 50
            isTimerRunning = false
        }
    }


    if (exp >= maxExp) {
        level++
        exp -= maxExp
        maxExp=(maxExp*2)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Level: $level ($exp/$maxExp)")
        StatBar(progress = expBarProgress, color = Color.Green)

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .weight(1f)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "HP: $ticks/$maxTicks")
            StatBar(progress = hpBarProgress, color = Color.Red)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    ticks = maxTicks
                    isTimerRunning = true
                },
                enabled = !isTimerRunning
            ) {
                Text(text = "Attack")
            }
            Button(
                onClick = { isTimerRunning = false },
                enabled = isTimerRunning
            ) {
                Text(text = "Pause")
            }
            Button(
                onClick = {
                    isTimerRunning = false
                    ticks = maxTicks
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun StatBar(progress: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(20.dp)
            .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress)
                .fillMaxHeight()
                .background(color = color, shape = RoundedCornerShape(10.dp))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BattleTimerScreenPreview() {
    timerLogic()
}