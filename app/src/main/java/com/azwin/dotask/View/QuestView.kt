package com.azwin.dotask.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azwin.dotask.R
import com.azwin.dotask.ViewModel.QuestViewModel

@Composable
fun QuestView(questViewModel: QuestViewModel = viewModel()) {
    val quests by questViewModel.quests.collectAsState()
    var newQuestText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.backgroundarena2),
            contentDescription = "Background 2",
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newQuestText,
                    onValueChange = { newQuestText = it },
                    label = { Text("Nama Quest Baru")},
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )
                Button(onClick = {
                    if (newQuestText.isNotBlank()) {
                        questViewModel.addQuest(newQuestText)
                        newQuestText = ""
                    }
                }, modifier = Modifier.padding(start = 8.dp)) {
                    Text("Add")
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f).padding(top = 16.dp)
            ) {
                items(quests, key = { it.id }) { quest ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).background(color = Color.Black)
                    ) {
                        Checkbox(
                            checked = quest.isCompleted,
                            onCheckedChange = {
                                questViewModel.toggleQuestCompleted(quest.id)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                        Text(
                            color = Color.White,
                            text = quest.task,
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        )
                        IconButton(onClick = { questViewModel.deleteQuest(quest.id) }) {
                            Icon(
                                Icons.Default.Delete, 
                                contentDescription = "Hapus Quest",
                                tint = Color.White 
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
@Preview(
    showBackground = true,
)
fun questpreview(){
    QuestView()
}
