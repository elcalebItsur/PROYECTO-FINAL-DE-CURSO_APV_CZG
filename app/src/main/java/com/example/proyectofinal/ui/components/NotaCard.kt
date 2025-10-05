package com.example.proyectofinal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofinal.data.model.Nota
import com.example.proyectofinal.data.model.TipoNota
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotaCard(
    nota: Nota,
    onNotaClick: (Nota) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onNotaClick(nota) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = nota.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nota.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
            
            if (nota.archivosMultimedia.isNotEmpty()) {
                Text(
                    text = "Archivos adjuntos: ${nota.archivosMultimedia.size}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            nota.fechaLimite?.let { fechaLimite ->
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                Text(
                    text = "Fecha límite: ${sdf.format(Date(fechaLimite))}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotaCardPreview() {
    val notaEjemplo = Nota(
        id = "1",
        titulo = "Nota de ejemplo",
        descripcion = "Esta es una descripción de ejemplo para la nota",
        fechaCreacion = System.currentTimeMillis(),
        archivosMultimedia = emptyList(),
        tipo = TipoNota.NOTA,
        fechaLimite = System.currentTimeMillis() + 86400000 // Mañana
    )
    
    MaterialTheme {
        NotaCard(
            nota = notaEjemplo,
            onNotaClick = {}
        )
    }
}