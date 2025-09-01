package com.azwin.dotask.View

import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azwin.dotask.R
import com.azwin.dotask.ViewModel.TimerViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun TimerView(timerViewModel: TimerViewModel = viewModel()) {

    val statistic by timerViewModel.statistic

    val expBarProgress = statistic.exp.toFloat() / statistic.maxExp
    val hpBarProgress = statistic.ticks.toFloat() / statistic.maxTicks

    //UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.backgroundarena),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Level: ${statistic.level} (${statistic.exp}/${statistic.maxExp})", color = Color.White)
                StatBar(progress = expBarProgress, color = Color.Green)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(1f)
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .alpha(0.8f),
                        painter = rememberDrawablePainter(
                            drawable = getDrawable(
                                LocalContext.current,
                                R.drawable.slimeghost
                            )
                        ),
                        contentDescription = "Loading animation",
                        contentScale = ContentScale.FillWidth,
                    )
                    Text(text = "HP: ${statistic.ticks}/${statistic.maxTicks}",color = Color.White)
                    StatBar(progress = hpBarProgress, color = Color.Red)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { timerViewModel.onAttackClicked() },
                        enabled = !statistic.isTimerRunning
                    ) {
                        Text(text = "Attack")
                    }
                    Button(
                        onClick = { timerViewModel.onPauseClicked() },
                        enabled = statistic.isTimerRunning
                    ) {
                        Text(text = "Pause")
                    }
                    Button(
                        onClick = { timerViewModel.onCancelClicked() },
                        enabled = statistic.ticks < statistic.maxTicks,
                    ) {
                        Text(text = "Cancel")
                    }
                }
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
            .border(width = 1.5.dp, color = Color.White, shape = RoundedCornerShape(10.dp))
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
fun TimerViewPreview() {
    TimerView()
}
