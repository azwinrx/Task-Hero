package com.azwin.dotask.Model

import com.azwin.dotask.Model.Fight.Statistic.PlayerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object PlayerRepository {

    // Satu-satunya sumber kebenaran untuk data pemain.
    // MutableStateFlow memungkinkan ViewModel untuk "mendengarkan" perubahan data.
    private val _playerState = MutableStateFlow(PlayerData())
    val playerState = _playerState.asStateFlow()

    // Fungsi terpusat untuk menambahkan EXP dari sumber manapun (Quest, Monster, dll)
    fun addExp(expToAdd: Int) {
        val newExp = _playerState.value.exp + expToAdd
        _playerState.value = _playerState.value.copy(exp = newExp)
        checkLevelUp()
        // Nanti kita bisa tambahkan savePlayerData() di sini
    }

    // Memeriksa apakah EXP cukup untuk naik level
    private fun checkLevelUp() {
        if (_playerState.value.exp >= _playerState.value.maxExp) {
            levelUpPlayer()
        }
    }

    // Logika untuk menaikkan level pemain
    private fun levelUpPlayer() {
        val currentPlayer = _playerState.value
        val remainingExp = currentPlayer.exp - currentPlayer.maxExp
        val newLevel = currentPlayer.level + 1

        // Membuat instance PlayerData baru untuk menghitung statistik berdasarkan level baru.
        val newStats = PlayerData(level = newLevel, exp = remainingExp)

        // Memperbarui state pemain dengan statistik yang baru
        // dan memulihkan stamina ke maksimum.
        _playerState.value = newStats.copy(stamina = newStats.MaxStamina)
    }
}