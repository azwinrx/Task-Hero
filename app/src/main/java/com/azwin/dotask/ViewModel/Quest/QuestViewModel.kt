package com.azwin.dotask.ViewModel.Quest

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.azwin.dotask.Model.PlayerRepository
import com.azwin.dotask.Model.Quest.ToDo

class QuestViewModel : ViewModel() {

    // State untuk daftar tugas, ini tetap spesifik untuk QuestView
    private val _toDoList = mutableStateListOf<ToDo>()
    val toDoList: List<ToDo> = _toDoList

    // Ambil state pemain langsung dari sumber tunggal (Single Source of Truth)
    val player = PlayerRepository.playerState

    private var lastId = 0

    fun addToDo(task: String) {
        _toDoList.add(ToDo(id = ++lastId, task = task, isCompleted = false))
    }

    fun removeToDo(toDo: ToDo) {
        _toDoList.remove(toDo)
    }

    // Fungsi ini sekarang didelegasikan ke Repositori
    fun toggleToDoCompletion(toDo: ToDo) {
        _toDoList.remove(toDo)
        // Panggil fungsi terpusat untuk menambah EXP
        PlayerRepository.addExp(100)
    }

    // Logika level up sudah tidak ada di sini, karena sudah terpusat di PlayerRepository
}
