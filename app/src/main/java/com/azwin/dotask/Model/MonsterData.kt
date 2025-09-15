package com.azwin.dotask.Model
import androidx.annotation.DrawableRes

data class MonsterData (
    val id: Int,
    val name: String,
    val maxHp: Int,
    @DrawableRes val imageRes: Int
)