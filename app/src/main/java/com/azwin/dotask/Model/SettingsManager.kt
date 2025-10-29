package com.azwin.dotask.Data // Sesuaikan package-mu

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.azwin.dotask.Model.Fight.Statistic.PlayerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_progress_final")

class SettingsManager(private val context: Context) {

    companion object {
        val PLAYER_LEVEL = intPreferencesKey("player_level")
        val PLAYER_EXP = intPreferencesKey("player_exp")
        val PLAYER_STAMINA = intPreferencesKey("player_stamina")
    }

    suspend fun savePlayerStats(player: PlayerData) {
        context.dataStore.edit { settings ->
            settings[PLAYER_LEVEL] = player.level
            settings[PLAYER_EXP] = player.exp
            settings[PLAYER_STAMINA] = player.stamina
        }
    }

    val playerStatsFlow: Flow<PlayerData> = context.dataStore.data.map { preferences ->
        val level = preferences[PLAYER_LEVEL] ?: 1
        val exp = preferences[PLAYER_EXP] ?: 0
        val stamina = preferences[PLAYER_STAMINA] ?: 1800

        //Recount starts based on level rn
        val maxExp = 100 * level
        val maxStamina = 1800

        //I make this damage to contant(static) for now, i will made adjustment weapone feature soon
        val damage = 1

        PlayerData(
            level = level,
            exp = exp,
            stamina = stamina,
            maxExp = maxExp,
            MaxStamina = maxStamina,
            damage = damage
        )
    }
}