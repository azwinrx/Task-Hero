package com.azwin.dotask.Model

data class TimerData (
    val level: Int = 1,
    val exp: Int = 0,
    val maxExp: Int = 100,
    val ticks: Int = 900,
    val maxTicks: Int = 900,
    val isTimerRunning: Boolean = false,
)