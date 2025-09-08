package com.azwin.dotask.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azwin.dotask.Model.TimerData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {

    private val _statistic = mutableStateOf(TimerData())
    val statistic: State<TimerData> = _statistic
    private var timerJob: Job? = null

    //action buat attack
    fun onAttackClicked() {
        timerJob?.cancel()
        _statistic.value = _statistic.value.copy(
            ticks = _statistic.value.ticks,
            isTimerRunning = true
        )
        startTimer()
    }

    //action buat pause
    fun onPauseClicked() {
        timerJob?.cancel()
        _statistic.value = _statistic.value.copy(isTimerRunning = false)
    }

    //action buat cancel
    fun onCancelClicked() {
        timerJob?.cancel()
        _statistic.value = _statistic.value.copy(
            ticks = _statistic.value.maxTicks,
            isTimerRunning = false
        )
    }

    //mulai timer
    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_statistic.value.ticks > 0) {
                delay(1.seconds)
                _statistic.value = _statistic.value.copy(ticks = _statistic.value.ticks - 1)
            }

            val currentStats = _statistic.value
            val newExp = currentStats.exp + 50
            _statistic.value = currentStats.copy(
                exp = newExp,
                isTimerRunning = false
            )
            checkLevelUp()
        }
    }

    //chek level naik atau ga
    private fun checkLevelUp() {
        val currentStats = _statistic.value
        if (currentStats.exp >= currentStats.maxExp) {
            _statistic.value = currentStats.copy(
                level = currentStats.level + 1,
                exp = currentStats.exp - currentStats.maxExp,
                maxExp = currentStats.maxExp * 2
            )
        }
    }

}
