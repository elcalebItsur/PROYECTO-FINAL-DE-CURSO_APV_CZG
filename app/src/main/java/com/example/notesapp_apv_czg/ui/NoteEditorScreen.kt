package com.example.notesapp_apv_czg.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp_apv_czg.data.Note
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import coil.compose.rememberAsyncImagePainter

@Composable
fun NoteEditorScreen(note: Note? = null, onSave: (Note) -> Unit = {}, onCancel: () -> Unit = {}) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }
    var isTask by remember { mutableStateOf(note?.isTask ?: false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") }, modifier = Modifier.padding(top = 8.dp))
        // Attachments preview row
        Row(modifier = Modifier.padding(top = 8.dp)) {
            val attachments = note?.attachmentUris ?: emptyList()
            for (uri in attachments) {
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).padding(end = 8.dp)
                )
            }
        }
        // TODO: Hook up actual attachment picker via ActivityResultLauncher
        Button(onClick = {
            val toSave = Note(id = note?.id ?: 0, title = title, description = description, isTask = isTask)
            onSave(toSave)
        }, modifier = Modifier.padding(top = 12.dp)) { Text("Guardar") }
        Button(onClick = onCancel, modifier = Modifier.padding(top = 8.dp)) { Text("Cancelar") }
    }
}
