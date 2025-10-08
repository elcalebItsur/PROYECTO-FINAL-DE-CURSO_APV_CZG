package com.example.proyectofinal.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun generarMiniatura(uri: Uri): Uri {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val miniatura = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
        // TODO: Guardar miniatura y devolver su URI
        return uri
    }

    fun generarMiniaturaVideo(uri: Uri): Uri {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        val bitmap = retriever.frameAtTime
        val miniatura = Bitmap.createScaledBitmap(bitmap!!, 200, 200, true)
        // TODO: Guardar miniatura y devolver su URI
        return uri
    }

    fun grabarAudio(): Uri {
        // TODO: Implementar grabación de audio
        return Uri.EMPTY
    }

    fun tomarFoto(): Uri {
        // TODO: Implementar captura de foto
        return Uri.EMPTY
    }

    fun grabarVideo(): Uri {
        // TODO: Implementar grabación de video
        return Uri.EMPTY
    }

    fun seleccionarArchivo(tipo: String): Uri {
        // TODO: Implementar selección de archivo
        return Uri.EMPTY
    }
}