package com.azwin.dotask.View

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
import androidx.compose.runtime.collectAsState
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
import com.azwin.dotask.Model.Fight.Statistic.MonsterData
import com.azwin.dotask.Model.Fight.Statistic.PlayerData
import com.azwin.dotask.Model.Fight.TimerData
import com.azwin.dotask.R
import com.azwin.dotask.View.Components.GameButton
import com.azwin.dotask.View.Components.StatisticBar
import com.azwin.dotask.ViewModel.Fight.TimerViewModel
import com.azwin.dotask.ui.theme.jersey25
import com.google.accompanist.drawablepainter.rememberDrawablePainter

// 1. STATEFUL Composable: Bertugas menyediakan data dari ViewModel
@Composable
fun TimerView(timerViewModel: TimerViewModel = viewModel()) {
    // Menggunakan collectAsState untuk mendengarkan StateFlow dari Repository
    val player by timerViewModel.player.collectAsState()
    val monster by timerViewModel.monster
    val monsterHp by timerViewModel.monsterHp
    val timer by timerViewModel.timer

    TimerViewContent(
        player = player,
        monster = monster,
        monsterHp = monsterHp,
        timer = timer,
        allMonsters = timerViewModel.allMonsters,
        onSelectMonster = timerViewModel::selectMonster,
        onAttackClicked = timerViewModel::onAttackClicked,
        onPauseClicked = timerViewModel::onPauseClicked,
        onRestClicked = timerViewModel::onRestClicked,
        onRestCancelClicked = timerViewModel::onRestCancelCLicked,
        onResetClicked = timerViewModel::onResetClicked
    )
}

// 2. STATELESS Composable: Hanya menampilkan UI
@Composable
fun TimerViewContent(
    player: PlayerData,
    monster: MonsterData,
    monsterHp: Int,
    timer: TimerData,
    allMonsters: List<MonsterData>,
    onSelectMonster: (MonsterData) -> Unit,
    onAttackClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    onRestClicked: () -> Unit,
    onRestCancelClicked: () -> Unit,
    onResetClicked: () -> Unit
) {
    var isMonsterDropdownExpanded by remember { mutableStateOf(false) }

    val expBarProgress = player.exp.toFloat() / player.maxExp
    val monsterHpProgress = monsterHp.toFloat() / monster.maxHp
    val playerStaminaProgress = player.stamina.toFloat() / player.MaxStamina

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroundarena1),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Player Stats Bars
            Column(
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
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
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier
                            .clip(RectangleShape)
                            .alpha(0.7f),
                        painter = rememberDrawablePainter(getDrawable(LocalContext.current, monster.imageRes)),
                        contentDescription = "Monster Image",
                        contentScale = ContentScale.FillWidth,
                    )
                    if (timer.isTimerRunning) {
                        Image(
                            modifier = Modifier
                                .clip(RectangleShape)
                                .alpha(0.8f),
                            painter = rememberDrawablePainter(getDrawable(LocalContext.current, R.drawable.hiteffect)),
                            contentDescription = "Hit Effect",
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }

                Box(contentAlignment = Alignment.TopCenter) {
                    Row(
                        modifier = Modifier
                            .clickable { isMonsterDropdownExpanded = true }
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = monster.name,
                            color = Color.White,
                            fontFamily = jersey25,
                            fontSize = 32.sp
                        )
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Monster",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = isMonsterDropdownExpanded,
                        onDismissRequest = { isMonsterDropdownExpanded = false }
                    ) {
                        allMonsters.forEach { monsterOption ->
                            DropdownMenuItem(
                                text = { Text(monsterOption.name) },
                                onClick = {
                                    onSelectMonster(monsterOption)
                                    isMonsterDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                StatisticBar(
                    progress = monsterHpProgress,
                    color = Color.Red,
                    text = "$monsterHp/${monster.maxHp}"
                )
            }

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box {
                    if (!timer.isTimerRunning) {
                        GameButton(backgroundRes = R.drawable.fightbutton, onClick = onAttackClicked, modifier = Modifier, enabled = true)
                    } else {
                        GameButton(backgroundRes = R.drawable.pausebutton, onClick = onPauseClicked, modifier = Modifier, enabled = true)
                    }
                }
                Box {
                    if (!timer.isTimerRunning && !timer.isRestRunning && player.stamina < player.MaxStamina) {
                        GameButton(backgroundRes = R.drawable.restbutton, onClick = onRestClicked, modifier = Modifier.alpha(1f), enabled = true)
                    } else if (timer.isRestRunning) {
                        GameButton(backgroundRes = R.drawable.restactive, onClick = onRestCancelClicked, modifier = Modifier.alpha(1f), enabled = true)
                    } else {
                        GameButton(backgroundRes = R.drawable.restbutton, onClick = {}, modifier = Modifier.alpha(0f), enabled = false)
                    }
                }
                Box {
                    if (monsterHp < monster.maxHp) {
                        GameButton(backgroundRes = R.drawable.cancelbutton, onClick = onResetClicked, modifier = Modifier.alpha(1f), enabled = true)
                    } else {
                        GameButton(backgroundRes = R.drawable.cancelbutton, onClick = {}, modifier = Modifier.alpha(0f), enabled = false)
                    }
                }
            }
        }
    }
}

// 3. Pratinjau sekarang memanggil versi stateless dengan data palsu
@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun TimerViewPreview() {
    val fakeMonster = MonsterData(id = 1, name = "Slime Ghost", maxHp = 900, expGain = 50, imageRes = R.drawable.slimeghost)
    val fakePlayer = PlayerData(level = 5, exp = 250, maxExp = 1000, stamina = 800, MaxStamina = 1000, damage = 150)

    TimerViewContent(
        player = fakePlayer,
        monster = fakeMonster,
        monsterHp = 800,
        timer = TimerData(),
        allMonsters = listOf(fakeMonster, MonsterData(id = 2, name = "Baby Lizard", maxHp = 1200, expGain = 100, imageRes = R.drawable.babylizard)),
        onSelectMonster = {},
        onAttackClicked = {},
        onPauseClicked = {},
        onRestClicked = {},
        onRestCancelClicked = {},
        onResetClicked = {}
    )
}
