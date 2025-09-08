package com.azwin.dotask.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azwin.dotask.Model.MonsterData
import com.azwin.dotask.Model.PlayerData
import com.azwin.dotask.Model.TimerData
import com.azwin.dotask.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    //Monster Detail
    private val allMonsters = listOf(
        MonsterData(id = 1, name = "Slime Ghost", maxHp = 300, imageRes = R.drawable.slimeghost),
        MonsterData(id = 2, name = "Baby Lizard", maxHp = 600, imageRes = R.drawable.babylizard),
        MonsterData(id = 3, name = "Evil Tree", maxHp = 900, imageRes = R.drawable.eviltree),
        MonsterData(id = 4, name = "The Unknown", maxHp = 1200, imageRes = R.drawable.theunknown),
    )

    private val _monster = mutableStateOf(allMonsters.first())
    val monster: State<MonsterData> = _monster

    private val _monsterHp = mutableStateOf(_monster.value.maxHp)
    val monsterHp: State<Int> = _monsterHp

    //Player Damage
    private val _player = mutableStateOf(PlayerData())
    val player: State<PlayerData> = _player
    private val _playerDamage = mutableStateOf(_player.value.damage)
    val playerDamage: State<Int> = _playerDamage

    //Timer Detail (Status)
    private val _timer = mutableStateOf(TimerData())
    val timer: State<TimerData> = _timer

    private var timerJob: Job? = null

    //action buat attack
    fun onAttackClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(
            isTimerRunning = true
        )
        _playerDamage.value = _player.value.damage
        startTimer()
    }

    //action buat pause
    fun onPauseClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(
            isTimerRunning = false
        )
    }

    //action buat reset
    fun onResetClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(
            isTimerRunning = false
        )
        _monsterHp.value = _monster.value.maxHp
    }

    //reset click
    fun onRestClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(
            isTimerRunning = false
        )
        timerJob = viewModelScope.launch {
            while (_player.value.stamina < _player.value.MaxStamina) {
                delay(1.seconds)
                _player.value = _player.value.copy(
                    stamina = _player.value.stamina + 1
                )
            }
        }
    }

    //fun reset stop
    fun resetStop() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(
            isTimerRunning = false
        )

    }


    //mulai timer
    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_monsterHp.value > 0) {
                delay(1.seconds)
                _monsterHp.value = _monsterHp.value.minus(_playerDamage.value)
                _player.value = _player.value.copy(
                    stamina = _player.value.stamina - 1
                )
            }
            val currentExp = _player.value
            val newExp = currentExp.exp + 50
            _player.value = currentExp.copy(
                exp = newExp,
            )
            _timer.value = _timer.value.copy(
                isTimerRunning = false
            )
            _monsterHp.value = _monster.value.maxHp
            checkLevelUp()

        }
    }


    //chek level naik atau ga
    private fun checkLevelUp() {
        val currentLevel = _player.value
        if (currentLevel.exp >= currentLevel.maxExp) {
            _player.value = currentLevel.copy(
                level = currentLevel.level + 1,
                exp = currentLevel.exp - currentLevel.maxExp,
                maxExp = currentLevel.maxExp * 2
            )
        }
    }

}
