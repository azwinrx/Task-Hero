package com.azwin.dotask.View
// import baru
// import baru
// import baru
// import baru
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

    var isMonsterDropdownExpanded by remember { mutableStateOf(false) }

    val monster by timerViewModel.monster
    val monsterHp by timerViewModel.monsterHp
    val player by timerViewModel.player
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
            // Player Stats Bars
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

            // Monster Display with Dropdown
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f)
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Box untuk gambar monster (agar tumpang tindih)
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .clip(RectangleShape)
                            .alpha(0.7f),
                        painter = rememberDrawablePainter(
                            drawable = getDrawable(
                                LocalContext.current,
                                monster.imageRes
                            )
                        ),
                        contentDescription = "Monster Image",
                        contentScale = ContentScale.FillWidth,
                    )
                    if (timer.isTimerRunning) {
                        Image(
                            modifier = Modifier
                                .clip(RectangleShape)
                                .alpha(0.8f),
                            painter = rememberDrawablePainter(
                                drawable = getDrawable(
                                    LocalContext.current,
                                    R.drawable.hiteffect
                                )
                            ),
                            contentDescription = "Hit Effect",
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }

                // Box ini membungkus Jangkar (Anchor) dan DropdownMenu
                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    // (Jangkar) Kita ganti Text menjadi Row agar bisa ada Ikon
                    Row(
                        modifier = Modifier
                            .clickable { isMonsterDropdownExpanded = true } // Pemicu
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Monster name
                        Text(
                            text = monster.name,
                            color = Color.White,
                            fontFamily = jersey25,
                            fontSize = 32.sp
                        )
                        // Ikon panah
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Monster",
                            tint = Color.White
                        )
                    }


                    // Dropdown Menu (Popup)
                    DropdownMenu(
                        expanded = isMonsterDropdownExpanded,
                        onDismissRequest = { isMonsterDropdownExpanded = false }
                    ) {
                        timerViewModel.allMonsters.forEach { monsterOption ->
                            DropdownMenuItem(
                                text = { Text(monsterOption.name) },
                                onClick = {
                                    timerViewModel.selectMonster(monsterOption)
                                    isMonsterDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Monster HP Bar
                StatisticBar(
                    progress = monsterHpProgress,
                    color = Color.Red,
                    text = "${monsterHp}/${monster.maxHp}"
                )
            }

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //Attack and pause button
                Box {
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
                Box {
                    if (!timer.isTimerRunning && !timer.isRestRunning && player.stamina < player.MaxStamina) {
                        GameButton(
                            backgroundRes = R.drawable.restbutton,
                            onClick = { timerViewModel.onRestClicked() },
                            modifier = Modifier.alpha(1f),
                            enabled = true
                        )

                    } else if (timer.isRestRunning) {
                        GameButton(
                            backgroundRes = R.drawable.restactive,
                            onClick = { timerViewModel.onRestCancelCLicked() },
                            modifier = Modifier.alpha(1f),
                            enabled = true
                        )

                    } else {
                        GameButton(
                            backgroundRes = R.drawable.restbutton,
                            onClick = {},
                            modifier = Modifier.alpha(0f),
                            enabled = false
                        )
                    }
                }

                //Cancel Button
                Box {
                    if (monsterHp < monster.maxHp) {
                        GameButton(
                            backgroundRes = R.drawable.cancelbutton,
                            onClick = { timerViewModel.onResetClicked() },
                            modifier = Modifier.alpha(1f),
                            enabled = true
                        )
                    } else {
                        GameButton(
                            backgroundRes = R.drawable.cancelbutton,
                            onClick = { },
                            modifier = Modifier.alpha(0f),
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