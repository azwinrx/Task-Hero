package com.azwin.dotask.ViewModel

// <<< PERUBAHAN 1: Tambahkan import yang dibutuhkan >>>
// ^^^ Ganti ViewModel dengan AndroidViewModel ^^^
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azwin.dotask.Data.SettingsManager
import com.azwin.dotask.Model.MonsterData
import com.azwin.dotask.Model.PlayerData
import com.azwin.dotask.Model.TimerData
import com.azwin.dotask.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsManager = SettingsManager(application)

    //Monster Detail
    private val allMonsters = listOf(
        MonsterData(id = 1, name = "Slime Ghost", maxHp = 900,expGain = 50, imageRes = R.drawable.slimeghost),
        MonsterData(id = 2, name = "Baby Lizard", maxHp = 1200, expGain = 100, imageRes = R.drawable.babylizard),
        MonsterData(id = 3, name = "Evil Tree", maxHp = 1500, expGain = 150, imageRes = R.drawable.eviltree),
        MonsterData(id = 4, name = "The Unknown", maxHp = 1800, expGain = 200, imageRes = R.drawable.theunknown),
    )

    private val _monster = mutableStateOf(allMonsters.first())
    val monster: State<MonsterData> = _monster

    private val _monsterHp = mutableStateOf(_monster.value.maxHp)
    val monsterHp: State<Int> = _monsterHp

    //Player Data
    private val _player = mutableStateOf(PlayerData()) // Dimulai dengan default
    val player: State<PlayerData> = _player
    private val _playerDamage = mutableStateOf(_player.value.damage)
    val playerDamage: State<Int> = _playerDamage

    //Timer Detail (Status)
    private val _timer = mutableStateOf(TimerData())
    val timer: State<TimerData> = _timer

    private var timerJob: Job? = null

    // <<< PERUBAHAN 4: Tambahkan init block untuk memuat data di awal >>>
    init {
        loadPlayerData()
    }

    //action buat attack
    fun onAttackClicked() {
        if (_player.value.stamina <= 0) return // Tambahan: Cek stamina sebelum menyerang

        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = true)
        _playerDamage.value = _player.value.damage
        startTimer()
    }

    //action buat pause
    fun onPauseClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false)
        // Saat pause, ada baiknya simpan progres stamina
        savePlayerData()
    }

    //action buat reset
    fun onResetClicked() {
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false)
        _monsterHp.value = _monster.value.maxHp
    }

    //rest click
    fun onRestClicked() {
        if (_timer.value.isTimerRunning) return // Tambahan: Jangan rest saat menyerang
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isTimerRunning = false, isRestRunning = true)
        timerJob = viewModelScope.launch {
            while (_player.value.stamina < _player.value.MaxStamina) {
                delay(1.seconds)
                _player.value = _player.value.copy(
                    stamina = (_player.value.stamina + 6).coerceAtMost(_player.value.MaxStamina)
                )
            }
            savePlayerData()
        }
    }

    fun onRestCancelCLicked(){
        timerJob?.cancel()
        _timer.value = _timer.value.copy(isRestRunning = false)
    }

    //mulai timer
    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_monsterHp.value > 0) {
                if (_player.value.stamina <= 0) {
                    onPauseClicked() // Otomatis pause jika stamina habis
                    break
                }
                delay(1.seconds)
                _monsterHp.value -= _playerDamage.value
                _player.value = _player.value.copy(stamina = _player.value.stamina - 1)
            }

            // Cek jika monster benar-benar kalah (bukan karena stamina habis)
            if (_monsterHp.value <= 0) {
                val currentExp = _player.value
                val newExp = currentExp.exp + 50
                _player.value = currentExp.copy(exp = newExp)

                checkLevelUp() // Pengecekan level up dipindah ke sini
            }

            _timer.value = _timer.value.copy(isTimerRunning = false)
            _monsterHp.value = _monster.value.maxHp

            // <<< PERUBAHAN 6: Simpan data setelah pertarungan selesai >>>
            savePlayerData()
        }
    }

    //chek level naik atau ga
    private fun checkLevelUp() {
        val currentPlayer = _player.value
        if (currentPlayer.exp >= currentPlayer.maxExp) {
            val remainingExp = currentPlayer.exp - currentPlayer.maxExp
            val newLevel = currentPlayer.level + 1

            // Ambil stats baru dari data yang dihitung di SettingsManager
            // Ini memastikan data konsisten dengan cara kita me-load nya
            val newStatsPlayer = PlayerData(level = newLevel, exp = remainingExp)

            _player.value = PlayerData(
                level = newLevel,
                exp = remainingExp,
                maxExp = newStatsPlayer.maxExp, // Ambil dari perhitungan baru
                stamina = newStatsPlayer.MaxStamina, // Pulihkan stamina
                MaxStamina = newStatsPlayer.MaxStamina,
                damage = newStatsPlayer.damage
            )
            // Tidak perlu save di sini karena sudah akan di-save di akhir startTimer()
        }
    }

    //buat load player data
    private fun loadPlayerData() {
        viewModelScope.launch {
            _player.value = settingsManager.playerStatsFlow.first()
            // Update juga player damage setelah load
            _playerDamage.value = _player.value.damage
        }
    }

    private fun savePlayerData() {
        viewModelScope.launch {
            settingsManager.savePlayerStats(_player.value)
        }
    }
}