package com.example.proyectofinal.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.proyectofinal.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val recordatorioChannel = NotificationChannel(
                CHANNEL_RECORDATORIOS,
                "Recordatorios",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones para recordatorios de tareas"
            }

            val tareaChannel = NotificationChannel(
                CHANNEL_TAREAS,
                "Tareas",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones para vencimiento de tareas"
            }

            notificationManager.createNotificationChannel(recordatorioChannel)
            notificationManager.createNotificationChannel(tareaChannel)
        }
    }

    fun mostrarNotificacionRecordatorio(titulo: String, mensaje: String) {
        if (!tienePermisoNotificaciones()) return

        val notification = NotificationCompat.Builder(context, CHANNEL_RECORDATORIOS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun mostrarNotificacionTarea(titulo: String, mensaje: String) {
        if (!tienePermisoNotificaciones()) return

        val notification = NotificationCompat.Builder(context, CHANNEL_TAREAS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun tienePermisoNotificaciones(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    companion object {
        const val CHANNEL_RECORDATORIOS = "recordatorios"
        const val CHANNEL_TAREAS = "tareas"
    }
}