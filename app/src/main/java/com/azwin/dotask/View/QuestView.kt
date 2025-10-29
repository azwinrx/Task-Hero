package com.azwin.dotask.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azwin.dotask.Model.Quest.ToDo // Import yang benar
import com.azwin.dotask.R
import com.azwin.dotask.ViewModel.Quest.QuestViewModel

// 1. STATEFUL Composable: Bertugas menyediakan data dari ViewModel
@Composable
fun QuestView(viewModel: QuestViewModel = viewModel()) {
    val toDoList = viewModel.toDoList
    var textState by remember { mutableStateOf("") }

    QuestViewContent(
        toDoList = toDoList,
        text = textState,
        onTextChange = { textState = it },
        onAddTask = {
            if (textState.isNotEmpty()) {
                viewModel.addToDo(textState)
                textState = "" // Reset input
            }
        },
        onToggleCompletion = { viewModel.toggleToDoCompletion(it) },
        onDeleteTask = { viewModel.removeToDo(it) }
    )
}

// 2. STATELESS Composable: Hanya menampilkan UI
@Composable
fun QuestViewContent(
    toDoList: List<ToDo>,
    text: String,
    onTextChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onToggleCompletion: (ToDo) -> Unit,
    onDeleteTask: (ToDo) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundarena2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 24.dp),
        ) {
            // Input field berada di atas
            AddToDoInput(
                text = text,
                onTextChange = onTextChange,
                onAddTask = onAddTask,
                modifier = Modifier.padding(16.dp)
            )

            // Daftar tugas mengisi sisa ruang
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(toDoList) { toDo ->
                    ToDoItem(
                        toDo = toDo,
                        onCheckChanged = { onToggleCompletion(toDo) },
                        onDelete = { onDeleteTask(toDo) }
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoItem(toDo: ToDo, onCheckChanged: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable { onCheckChanged() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = toDo.isCompleted,
            onCheckedChange = { onCheckChanged() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = toDo.task,
            style = MaterialTheme.typography.bodyLarge,
            color = if (toDo.isCompleted) Color.Gray else Color.White,
            textDecoration = if (toDo.isCompleted) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Task", tint = Color.White)
        }
    }
}

@Composable
fun AddToDoInput(text: String, onTextChange: (String) -> Unit, onAddTask: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("Add a new quest...") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onAddTask) {
            Icon(Icons.Filled.Add, contentDescription = "Add Task")
        }
    }
}

// 3. Pratinjau sekarang memanggil versi stateless dengan data palsu
@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun QuestViewPreview() {
    val sampleTasks = remember {
        mutableStateOf(
            listOf(
                ToDo(1, "Defeat the Slime King", false),
                ToDo(2, "Collect 10 Fire Crystals", true),
                ToDo(3, "Find the hidden treasure", false)
            )
        )
    }
    var text by remember { mutableStateOf("") }

    QuestViewContent(
        toDoList = sampleTasks.value,
        text = text,
        onTextChange = { text = it },
        onAddTask = {
            val newId = (sampleTasks.value.maxOfOrNull { it.id } ?: 0) + 1
            sampleTasks.value = sampleTasks.value + ToDo(newId, text, false)
            text = ""
        },
        onToggleCompletion = { taskToToggle ->
            sampleTasks.value = sampleTasks.value.map {
                if (it.id == taskToToggle.id) it.copy(isCompleted = !it.isCompleted) else it
            }
        },
        onDeleteTask = { taskToDelete ->
            sampleTasks.value = sampleTasks.value.filter { it.id != taskToDelete.id }
        }
    )
}
