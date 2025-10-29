package com.azwin.dotask.ViewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.azwin.dotask.Model.Statistic.MonsterData
import com.azwin.dotask.Model.Statistic.PlayerData
import com.azwin.dotask.Model.System.TimerData
import com.azwin.dotask.R


/*I making this ViewModel Class because i need the static value for Jetpack Compose Design,
Because there was an error when i use the TimerViewModel in TimerView
*/

class FakeTimerViewModel : TimerViewModel(Application()) {
    override val monster = mutableStateOf(
        MonsterData(
            id = 1,
            name = "Slime Ghost",
            maxHp = 900,
            expGain = 50,
            imageRes = R.drawable.slimeghost
        )
    )
    override val monsterHp = mutableStateOf(monster.value.maxHp)
    override val player = mutableStateOf(PlayerData(level = 5, exp = 250, maxExp = 1000, stamina = 50, MaxStamina = 100, damage = 150))
    override val timer = mutableStateOf(TimerData(isTimerRunning = false, isRestRunning = false))
    override val playerDamage = mutableStateOf(player.value.damage)

    override fun selectMonster(selectedMonster: MonsterData) {}
    override fun onAttackClicked() {}
    override fun onPauseClicked() {}
    override fun onResetClicked() {}
    override fun onRestClicked() {}
    override fun onRestCancelCLicked() {}

    // Override jadi tidak melakukan apa-apa saat digunakan
    override fun loadPlayerData() {}
    override fun savePlayerData() {}
}
