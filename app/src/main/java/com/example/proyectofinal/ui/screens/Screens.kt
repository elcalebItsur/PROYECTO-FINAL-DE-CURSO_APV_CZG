package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.data.model.Nota
import com.example.proyectofinal.ui.components.NotaCard

@Composable
fun ListaNotasScreen(
    onNotaClick: (String) -> Unit,
    onNuevaNotaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar lista de notas
    Box(modifier = modifier.fillMaxSize()) {
        Text("Lista de Notas - Por implementar")
    }
}

@Composable
fun CrearNotaScreen(
    onNotaCreada: (String) -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar creación de nota
    Box(modifier = modifier.fillMaxSize()) {
        Text("Crear Nota - Por implementar")
    }
}

@Composable
fun EditarNotaScreen(
    notaId: String,
    onNotaActualizada: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar edición de nota
    Box(modifier = modifier.fillMaxSize()) {
        Text("Editar Nota - Por implementar")
    }
}

@Composable
fun DetalleNotaScreen(
    notaId: String,
    onEditarClick: () -> Unit,
    onRecordatoriosClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar detalle de nota
    Box(modifier = modifier.fillMaxSize()) {
        Text("Detalle de Nota - Por implementar")
    }
}

@Composable
fun RecordatoriosScreen(
    notaId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar gestión de recordatorios
    Box(modifier = modifier.fillMaxSize()) {
        Text("Recordatorios - Por implementar")
    }
}

@Composable
fun AjustesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implementar ajustes
    Box(modifier = modifier.fillMaxSize()) {
        Text("Ajustes - Por implementar")
    }
}