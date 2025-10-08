package com.example.proyectofinal.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.proyectofinal.data.repository.NotasRepository
import com.example.proyectofinal.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class RecordatoriosWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: NotasRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val currentTime = System.currentTimeMillis()
            val recordatorios = repository.getPendingRecordatorios(currentTime).first()

            recordatorios.forEach { recordatorio ->
                val nota = repository.getNota(recordatorio.notaId) ?: return@forEach
                
                notificationHelper.mostrarNotificacionRecordatorio(
                    titulo = nota.titulo,
                    mensaje = "¡Tienes un recordatorio pendiente!"
                )

                // Actualizar el recordatorio como notificado
                repository.updateNota(nota.copy(
                    recordatorios = nota.recordatorios.map {
                        if (it.id == recordatorio.id) it.copy(notificada = true)
                        else it
                    }
                ))
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "recordatorios_worker"
    }
}