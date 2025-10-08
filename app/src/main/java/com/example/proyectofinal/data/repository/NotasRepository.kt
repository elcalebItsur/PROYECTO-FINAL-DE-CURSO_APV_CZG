package com.example.proyectofinal.data.repository

import com.example.proyectofinal.data.database.dao.*
import com.example.proyectofinal.data.database.entities.*
import com.example.proyectofinal.data.model.*
import kotlinx.coroutines.flow.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasRepository @Inject constructor(
    private val notaDao: NotaDao,
    private val archivoMultimediaDao: ArchivoMultimediaDao,
    private val recordatorioDao: RecordatorioDao
) {
    fun getAllNotas(): Flow<List<Nota>> = notaDao.getAllNotas()
        .combine(getAllArchivosAndRecordatorios()) { notas, archivosYRecordatorios ->
            notas.map { notaEntity ->
                notaEntity.toNota(
                    archivosYRecordatorios.first[notaEntity.id] ?: emptyList(),
                    archivosYRecordatorios.second[notaEntity.id] ?: emptyList()
                )
            }
        }

    private fun getAllArchivosAndRecordatorios(): Flow<Pair<Map<String, List<ArchivoMultimediaEntity>>, Map<String, List<RecordatorioEntity>>>> {
        return flow {
            // Implementación pendiente
            emit(Pair(emptyMap(), emptyMap()))
        }
    }

    suspend fun getNota(id: String): Nota? {
        val notaEntity = notaDao.getNota(id) ?: return null
        val archivos = archivoMultimediaDao.getArchivosByNotaId(id).first()
        val recordatorios = recordatorioDao.getRecordatoriosByNotaId(id).first()
        return notaEntity.toNota(archivos, recordatorios)
    }

    suspend fun insertNota(nota: Nota) {
        notaDao.insertNota(nota.toEntity())
        nota.archivosMultimedia.forEach { archivo ->
            archivoMultimediaDao.insertArchivo(archivo.toEntity(nota.id))
        }
        nota.recordatorios.forEach { recordatorio ->
            recordatorioDao.insertRecordatorio(recordatorio.toEntity(nota.id))
        }
    }

    suspend fun updateNota(nota: Nota) {
        notaDao.updateNota(nota.toEntity())
        archivoMultimediaDao.deleteArchivosByNotaId(nota.id)
        recordatorioDao.deleteRecordatoriosByNotaId(nota.id)
        nota.archivosMultimedia.forEach { archivo ->
            archivoMultimediaDao.insertArchivo(archivo.toEntity(nota.id))
        }
        nota.recordatorios.forEach { recordatorio ->
            recordatorioDao.insertRecordatorio(recordatorio.toEntity(nota.id))
        }
    }

    suspend fun deleteNota(nota: Nota) {
        notaDao.deleteNota(nota.toEntity())
        // Los archivos y recordatorios se eliminarán en cascada
    }

    fun searchNotas(query: String): Flow<List<Nota>> =
        notaDao.searchNotas(query)
            .combine(getAllArchivosAndRecordatorios()) { notas, archivosYRecordatorios ->
                notas.map { notaEntity ->
                    notaEntity.toNota(
                        archivosYRecordatorios.first[notaEntity.id] ?: emptyList(),
                        archivosYRecordatorios.second[notaEntity.id] ?: emptyList()
                    )
                }
            }

    fun getPendingRecordatorios(timestamp: Long): Flow<List<Recordatorio>> =
        recordatorioDao.getPendingRecordatorios(timestamp)
            .map { recordatorios ->
                recordatorios.map { it.toRecordatorio() }
            }
}

// Extension functions para convertir entre modelos y entidades
private fun NotaEntity.toNota(
    archivos: List<ArchivoMultimediaEntity>,
    recordatorios: List<RecordatorioEntity>
): Nota = Nota(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    fechaCreacion = fechaCreacion,
    archivosMultimedia = archivos.map { it.toArchivoMultimedia() },
    tipo = tipo,
    fechaLimite = fechaLimite,
    recordatorios = recordatorios.map { it.toRecordatorio() },
    completada = completada
)

private fun Nota.toEntity() = NotaEntity(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    fechaCreacion = fechaCreacion,
    tipo = tipo,
    fechaLimite = fechaLimite,
    completada = completada
)

private fun ArchivoMultimediaEntity.toArchivoMultimedia() = ArchivoMultimedia(
    id = id,
    tipo = TipoArchivo.valueOf(tipo),
    uri = uri,
    descripcion = descripcion,
    fechaCreacion = fechaCreacion
)

private fun ArchivoMultimedia.toEntity(notaId: String) = ArchivoMultimediaEntity(
    id = id,
    notaId = notaId,
    tipo = tipo.name,
    uri = uri,
    descripcion = descripcion,
    fechaCreacion = fechaCreacion,
    miniatura = null // TODO: Implementar generación de miniaturas
)

private fun RecordatorioEntity.toRecordatorio() = Recordatorio(
    id = id,
    fechaHora = fechaHora,
    notificada = notificada
)

private fun Recordatorio.toEntity(notaId: String) = RecordatorioEntity(
    id = id,
    notaId = notaId,
    fechaHora = fechaHora,
    notificada = notificada
)