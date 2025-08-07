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

    // Countdown Timer in Second

    var buttonEnabled by remember { mutableStateOf(false) }
    var hpBar by remember { mutableStateOf(1.toFloat()) }
    var ticks by remember {mutableStateOf(900)}

    if (buttonEnabled == true){
        LaunchedEffect(Unit) {
            while (ticks > 0 ) {
                delay(1.seconds)
                ticks--
                hpBar = ticks/900f
            }
        }
    }


    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
        )
    {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.75f),
            verticalArrangement = Arrangement.Center
            )
        {
            Text(text = "HP: $ticks"
            )

            Box(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 30.dp)
                    .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(hpBar)
                        .fillMaxHeight(0.05f)
                        .background(color = Color.Red,shape = RoundedCornerShape(10.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(
                    onClick = {buttonEnabled = true},
                    modifier = Modifier
                ){
                    Text(text = "Attack")
                }
                Button(
                    onClick = {buttonEnabled = false},
                    modifier = Modifier
                ){
                    Text(text = "Pause")
                }
                Button(
                    onClick = {ticks = 900},
                    modifier = Modifier
                ){
                    Text(text = "Cancel")
                }
            }


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