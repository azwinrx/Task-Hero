// Di file baru, misal: app/src/main/java/com/azwin/dotask/Data/QuestRepository.kt
package com.azwin.dotask.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.azwin.dotask.Model.Quest.QuestData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quest_data")

class QuestRepository(private val context: Context) {

    private val gson = Gson()

    private val QUEST_LIST_KEY = stringPreferencesKey("quest_list_json")

    val quests: Flow<List<QuestData>> = context.dataStore.data.map { preferences ->
        val jsonString = preferences[QUEST_LIST_KEY]
        if (jsonString != null) {
            val type = object : TypeToken<List<QuestData>>() {}.type
            gson.fromJson(jsonString, type)
        } else {

            emptyList()
        }
    }

    suspend fun saveQuests(quests: List<QuestData>) {
        val jsonString = gson.toJson(quests)
        context.dataStore.edit { preferences ->
            preferences[QUEST_LIST_KEY] = jsonString
        }
    }
}
