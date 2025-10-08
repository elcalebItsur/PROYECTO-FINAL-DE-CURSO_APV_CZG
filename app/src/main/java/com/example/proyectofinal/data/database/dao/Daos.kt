package com.example.proyectofinal.data.database.dao

import androidx.room.*
import com.example.proyectofinal.data.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas ORDER BY CASE WHEN tipo = 'TAREA' THEN fechaLimite ELSE fechaCreacion END")
    fun getAllNotas(): Flow<List<NotaEntity>>
    
    @Query("SELECT * FROM notas WHERE id = :id")
    suspend fun getNota(id: String): NotaEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(nota: NotaEntity)
    
    @Update
    suspend fun updateNota(nota: NotaEntity)
    
    @Delete
    suspend fun deleteNota(nota: NotaEntity)
    
    @Query("SELECT * FROM notas WHERE titulo LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%'")
    fun searchNotas(query: String): Flow<List<NotaEntity>>
}

@Dao
interface ArchivoMultimediaDao {
    @Query("SELECT * FROM archivos_multimedia WHERE notaId = :notaId")
    fun getArchivosByNotaId(notaId: String): Flow<List<ArchivoMultimediaEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchivo(archivo: ArchivoMultimediaEntity)
    
    @Delete
    suspend fun deleteArchivo(archivo: ArchivoMultimediaEntity)
    
    @Query("DELETE FROM archivos_multimedia WHERE notaId = :notaId")
    suspend fun deleteArchivosByNotaId(notaId: String)
}

@Dao
interface RecordatorioDao {
    @Query("SELECT * FROM recordatorios WHERE notaId = :notaId ORDER BY fechaHora")
    fun getRecordatoriosByNotaId(notaId: String): Flow<List<RecordatorioEntity>>
    
    @Query("SELECT * FROM recordatorios WHERE fechaHora <= :timestamp AND notificada = 0")
    fun getPendingRecordatorios(timestamp: Long): Flow<List<RecordatorioEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordatorio(recordatorio: RecordatorioEntity)
    
    @Update
    suspend fun updateRecordatorio(recordatorio: RecordatorioEntity)
    
    @Delete
    suspend fun deleteRecordatorio(recordatorio: RecordatorioEntity)
    
    @Query("DELETE FROM recordatorios WHERE notaId = :notaId")
    suspend fun deleteRecordatoriosByNotaId(notaId: String)
}