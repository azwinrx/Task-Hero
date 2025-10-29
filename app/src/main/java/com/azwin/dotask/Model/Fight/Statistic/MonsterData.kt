package com.azwin.dotask.Model.Fight.Statistic

import androidx.annotation.DrawableRes

data class MonsterData(
    val id: Int,
    val name: String,
    val maxHp: Int,
    val expGain: Int,
    @DrawableRes val imageRes: Int
)