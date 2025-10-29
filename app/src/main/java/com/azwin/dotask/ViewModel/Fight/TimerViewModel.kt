package com.azwin.dotask.ViewModel.Fight

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azwin.dotask.Model.Fight.Statistic.MonsterData
import com.azwin.dotask.Model.Fight.TimerData
import com.azwin.dotask.Model.PlayerRepository
import com.azwin.dotask.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

open class TimerViewModel(application: Application) : AndroidViewModel(application) {

    //Monster Detail
    val allMonsters = listOf(
        MonsterData(
            id = 1,
            name = "Slime Ghost",
            maxHp = 900,
            expGain = 50,
            imageRes = R.drawable.slimeghost
        ),
        MonsterData(
            id = 2,
            name = "Baby Lizard",
            maxHp = 1200,
            expGain = 100,
            imageRes = R.drawable.babylizard
        ),
        MonsterData(
            id = 3,
            name = "Evil Tree",
            maxHp = 1500,
            expGain = 150,
            imageRes = R.drawable.eviltree
        ),
        MonsterData(
            id = 4,
            name = "The Unknown",
            maxHp = 1800,
            expGain = 200,
            imageRes = R.drawable.theunknown
        ),
    )

    private val _monster = mutableStateOf(allMonsters.first())
    open val monster: State<MonsterData> = _monster

    private val _monsterHp = mutableStateOf(_monster.value.maxHp)
    open val monsterHp: State<Int> = _monsterHp

    // Ambil state pemain langsung dari sumber tunggal (Single Source of Truth)
    val player = PlayerRepository.playerState

    private val _playerDamage = mutableStateOf(player.value.damage)
    open val playerDamage: State<Int> = _playerDamage

    //Timer Detail (Status)
    private val _timer = mutableStateOf(TimerData())
    open val timer: State<TimerData> = _timer

    private var timerJob: Job? = null

    open fun selectMonster(selectedMonster: MonsterData) {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false, isRestRunning = false)
        _monster.value = selectedMonster
        _monsterHp.value = selectedMonster.maxHp
    }

    //Action for attack
    open fun onAttackClicked() {
        if (player.value.stamina <= 0) return

        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = true)
        _playerDamage.value = player.value.damage
        startTimer()
    }

    //Action for pause
    open fun onPauseClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false)
    }

    //Action for reset
    open fun onResetClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false)
        _monsterHp.value = _monster.value.maxHp
    }

    //rest click
    open fun onRestClicked() {
        if (_timer.value.isTimerRunning) return
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false, isRestRunning = true)
        timerJob = viewModelScope.launch {
            while (player.value.stamina < player.value.MaxStamina) {
                delay(1.seconds)
                // Implementasi penambahan stamina perlu dipindahkan ke Repository nanti
            }
            _timer.value = _timer.value.copy(isRestRunning = false)
        }
    }

    open fun onRestCancelCLicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isRestRunning = false)
    }

    //Start timer
    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_monsterHp.value > 0) {
                if (player.value.stamina <= 0) {
                    onPauseClicked()
                    break
                }
                delay(1.seconds)
                _monsterHp.value -= _playerDamage.value
                // Implementasi pengurangan stamina perlu dipindahkan ke Repository nanti
            }

            if (_monsterHp.value <= 0) {
                // Panggil fungsi terpusat untuk menambah EXP
                PlayerRepository.addExp(monster.value.expGain)
            }

            _timer.value = _timer.value.copy(isTimerRunning = false)
            _monsterHp.value = _monster.value.maxHp
        }
    }
    // Logika level up sudah tidak ada di sini, karena sudah terpusat di PlayerRepository
}
