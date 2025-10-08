package com.example.proyectofinal.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectofinal.data.model.TipoNota

@Entity(tableName = "notas")
data class NotaEntity(
    @PrimaryKey
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fechaCreacion: Long,
    val tipo: TipoNota,
    val fechaLimite: Long?,
    val completada: Boolean = false,
    val orden: Int = 0 // Para mantener el orden personalizado
)

@Entity(tableName = "archivos_multimedia")
data class ArchivoMultimediaEntity(
    @PrimaryKey
    val id: String,
    val notaId: String,
    val tipo: String, // "IMAGEN", "VIDEO", "AUDIO"
    val uri: String,
    val descripcion: String?,
    val fechaCreacion: Long,
    val miniatura: String? // URI de la miniatura
)

@Entity(tableName = "recordatorios")
data class RecordatorioEntity(
    @PrimaryKey
    val id: String,
    val notaId: String,
    val fechaHora: Long,
    val notificada: Boolean = false,
    val mensaje: String? = null
)