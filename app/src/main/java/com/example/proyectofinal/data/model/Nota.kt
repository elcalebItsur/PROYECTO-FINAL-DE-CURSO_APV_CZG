package com.example.proyectofinaldata class Recordatorio(
    val id: String,
    val fechaHora: Long, // Timestamp en milisegundos
    val notificada: Boolean = false)a.model

data class Nota(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fechaCreacion: Long, // Timestamp en milisegundos
    val archivosMultimedia: List<ArchivoMultimedia>,
    val tipo: TipoNota,
    val fechaLimite: Long? = null, // Timestamp en milisegundos
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
    val fechaCreacion: Long // Timestamp en milisegundos
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