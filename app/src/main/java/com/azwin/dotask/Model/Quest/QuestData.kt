package com.azwin.dotask.Model.Quest

data class QuestData (
    val id: Int,
    val task: String,
    var isCompleted: Boolean = false
)