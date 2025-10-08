package com.example.notesapp_apv_czg.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp_apv_czg.data.Note

@Composable
fun NoteListScreen(notes: List<Note>, onAdd: () -> Unit = {}, onOpen: (Long) -> Unit = {}, onSearch: (String) -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = query, onValueChange = { query = it; onSearch(it) }, label = { Text("Buscar") })
        Button(onClick = onAdd, modifier = Modifier.padding(top = 8.dp)) { Text("Nueva nota/tarea") }
        LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
            items(notes) { note ->
                NoteRow(note = note, onClick = { onOpen(note.id) })
            }
        }
    }
}

@Composable
fun NoteRow(note: Note, onClick: () -> Unit) {
    Column(modifier = Modifier
        .clickable { onClick() }
        .padding(8.dp)) {
        Text(text = note.title)
        note.description?.let { Text(text = it) }
    }
}
