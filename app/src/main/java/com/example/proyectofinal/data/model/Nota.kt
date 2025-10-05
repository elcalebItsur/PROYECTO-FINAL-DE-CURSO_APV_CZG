package com.example.proyectofinal.data.model

import java.time.LocalDateTime

data class Nota(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fechaCreacion: LocalDateTime,
    val archivosMultimedia: List<ArchivoMultimedia>,
    val tipo: TipoNota,
    val fechaLimite: LocalDateTime? = null,
    val recordatorios: List<Recordatorio> = emptyList(),
    val completada: Boolean = false
)

enum class TipoNota {
    NOTA,
    TAREA
}

data class ArchivoMultimedia(
    val id: String,
    val tipo: TipoArchivo,
    val uri: String,
    val descripcion: String?,
    val fechaCreacion: LocalDateTime
)

enum class TipoArchivo {
    IMAGEN,
    VIDEO,
    AUDIO
}

data class Recordatorio(
    val id: String,
    val fechaHora: LocalDateTime,
    val notificada: Boolean = false
)