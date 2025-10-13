package com.azwin.dotask.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azwin.dotask.R
import com.azwin.dotask.View.Components.GameButton
import com.azwin.dotask.View.Components.StatisticBar
import com.azwin.dotask.ViewModel.TimerViewModel
import com.azwin.dotask.ui.theme.jersey25
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun TimerView(timerViewModel: TimerViewModel = viewModel()) {

    val monster by timerViewModel.monster
    val monsterHp by timerViewModel.monsterHp
    val player by timerViewModel.player
    val playerDamage by timerViewModel.playerDamage
    val timer by timerViewModel.timer

    val expBarProgress = player.exp.toFloat() / player.maxExp
    val monsterHpProgress = monsterHp.toFloat() / monster.maxHp
    val playerStaminaProgress = player.stamina.toFloat() / player.MaxStamina

    //UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroundarena),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                ) {
                    StatisticBar(
                        progress = expBarProgress,
                        color = Color.Green,
                        text = "Level : ${player.level} (${player.exp}/${player.maxExp})"
                    )
                    StatisticBar(
                        progress = playerStaminaProgress,
                        color = Color.Blue,
                        text = "${player.stamina}/${player.MaxStamina}"
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(1f)
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .alpha(0.7f),
                            painter = rememberDrawablePainter(
                                drawable = getDrawable(
                                    LocalContext.current,
                                    monster.imageRes
                                )
                            ),
                            contentDescription = "Loading animation",
                            contentScale = ContentScale.FillWidth,
                        )
                        if (timer.isTimerRunning) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .alpha(0.8f),
                                painter = rememberDrawablePainter(
                                    drawable = getDrawable(
                                        LocalContext.current,
                                        R.drawable.hiteffect
                                    )
                                ),
                                contentDescription = "Loading animation",
                                contentScale = ContentScale.FillWidth,
                            )
                        }
                    }
                    Text(text = monster.name, color = Color.White, fontFamily = jersey25, fontSize = 32.sp)
                    StatisticBar(
                        progress = monsterHpProgress,
                        color = Color.Red,
                        text = "${monsterHp}/${monster.maxHp}"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //Attack and pause button
                    Box(
                        modifier = Modifier
                    ) {
                        if (!timer.isTimerRunning) {
                            GameButton(
                                backgroundRes = R.drawable.fightbutton,
                                onClick = { timerViewModel.onAttackClicked() },
                                modifier = Modifier,
                                enabled = true
                            )
                        } else {
                            GameButton(
                                backgroundRes = R.drawable.pausebutton,
                                onClick = { timerViewModel.onPauseClicked() },
                                modifier = Modifier,
                                enabled = true
                            )
                        }
                    }
                    //Rest Button
                    Box() {
                        if (!timer.isTimerRunning && !timer.isRestRunning) {
                            GameButton(
                                backgroundRes = R.drawable.restbutton,
                                onClick = { timerViewModel.onRestClicked() },
                                modifier = Modifier
                                    .alpha(1f),
                                enabled = true
                            )
                        }else if(timer.isRestRunning){
                            GameButton(
                                backgroundRes = R.drawable.restactive,
                                onClick = { timerViewModel.onRestCancelCLicked()},
                                modifier = Modifier
                                    .alpha(1f),
                                enabled = true
                            )
                        }
                        else{
                            GameButton(
                                backgroundRes = R.drawable.restbutton,
                                onClick = {},
                                modifier = Modifier
                                    .alpha(0f),
                                enabled = false
                            )
                        }
                    }
                    //Cancel Button
                    Box(){
                        if (monsterHp < monster.maxHp){
                            GameButton(
                                backgroundRes = R.drawable.cancelbutton,
                                onClick = { timerViewModel.onResetClicked() },
                                modifier = Modifier
                                    .alpha(1f),
                                enabled = true
                            )
                        }else{
                            GameButton(
                                backgroundRes = R.drawable.cancelbutton,
                                onClick = { },
                                modifier = Modifier
                                    .alpha(0f),
                                enabled = false
                            )
                        }
                    }
                }
            }
        }
    }




@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun TimerViewPreview() {
    TimerView()
}
