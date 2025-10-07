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
    modifier: Modifier = Modifier,
    viewModel: NotasViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val notas by viewModel.notas.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (notas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "No hay notas. ¡Crea una!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notas) { nota ->
                    NotaCard(
                        nota = nota,
                        onNotaClick = { onNotaClick(nota.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearNotaScreen(
    onNotaCreada: (String) -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotasViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipoNota by remember { mutableStateOf(TipoNota.NOTA) }
    var fechaLimite by remember { mutableStateOf<Long?>(null) }
    var mostrarDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Cancelar")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val id = viewModel.crearNota(titulo, descripcion, tipoNota, fechaLimite)
                            onNotaCreada(id)
                        },
                        enabled = titulo.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipoNota == TipoNota.NOTA,
                        onClick = { tipoNota = TipoNota.NOTA }
                    )
                    Text("Nota")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipoNota == TipoNota.TAREA,
                        onClick = { tipoNota = TipoNota.TAREA }
                    )
                    Text("Tarea")
                }
            }

            if (tipoNota == TipoNota.TAREA) {
                OutlinedButton(
                    onClick = { mostrarDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (fechaLimite != null)
                            "Fecha límite: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(fechaLimite!!))}"
                        else
                            "Establecer fecha límite"
                    )
                }
            }
        }
    }

    if (mostrarDatePicker) {
        // TODO: Implementar DatePicker personalizado
        // Por ahora usamos un timestamp del día siguiente
        fechaLimite = System.currentTimeMillis() + 86400000
        mostrarDatePicker = false
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleNotaScreen(
    notaId: String,
    onEditarClick: () -> Unit,
    onRecordatoriosClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotasViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val nota by viewModel.notaActual.collectAsState()
    
    LaunchedEffect(notaId) {
        viewModel.cargarNota(notaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nota?.titulo ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onEditarClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    if (nota?.tipo == TipoNota.TAREA) {
                        IconButton(onClick = onRecordatoriosClick) {
                            Icon(Icons.Default.Alarm, contentDescription = "Recordatorios")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        nota?.let { nota ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = nota.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                nota.fechaLimite?.let { fechaLimite ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Schedule, contentDescription = null)
                            Text(
                                text = "Fecha límite: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(fechaLimite))}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                if (nota.archivosMultimedia.isNotEmpty()) {
                    Text(
                        text = "Archivos adjuntos",
                        style = MaterialTheme.typography.titleMedium
                    )
                    // TODO: Implementar visualización de archivos multimedia
                }
            }
        }
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