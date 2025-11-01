package com.azwin.dotask.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azwin.dotask.Data.QuestRepository
import com.azwin.dotask.Model.Quest.QuestData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuestRepository(application)

    private val _quests = MutableStateFlow<List<QuestData>>(emptyList())
    val quests: StateFlow<List<QuestData>> = _quests

    init {
        viewModelScope.launch {
            repository.quests.collect { questList ->
                _quests.value = questList
            }
        }
    }

    private fun saveCurrentQuests() {
        viewModelScope.launch {
            repository.saveQuests(_quests.value)
        }
    }

    fun addQuest(task: String) {
        val newId = (_quests.value.maxOfOrNull { it.id } ?: 0) + 1
        val newQuest = QuestData(id = newId, task = task)
        _quests.value = _quests.value + newQuest
        saveCurrentQuests()
    }

    fun toggleQuestCompleted(questId: Int) {
        _quests.value = _quests.value.map { quest ->
            if (quest.id == questId) {
                quest.copy(isCompleted = !quest.isCompleted)
            } else {
                quest
            }
        }
        saveCurrentQuests()
    }

    fun deleteQuest(questId: Int) {
        _quests.value = _quests.value.filterNot { it.id == questId }
        saveCurrentQuests()
    }
}
