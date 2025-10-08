package com.example.proyectofinal.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.model.*
import com.example.proyectofinal.data.repository.NotasRepository
import com.example.proyectofinal.util.MediaManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NotasViewModel @Inject constructor(
    private val repository: NotasRepository,
    private val mediaManager: MediaManager
) : ViewModel()

class NotasViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NotasUiState())
    val uiState: StateFlow<NotasUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var currentNotaId: String? = null

    init {
        viewModelScope.launch {
            repository.getAllNotas()
                .catch { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
                .collect { notas ->
                    _uiState.update { 
                        it.copy(
                            notas = notas,
                            filteredNotas = if (_searchQuery.value.isBlank()) notas 
                                else notas.filter { nota -> 
                                    nota.titulo.contains(_searchQuery.value, true) ||
                                    nota.descripcion.contains(_searchQuery.value, true)
                                }
                        )
                    }
                }
        }
    }

    fun createNota(
        titulo: String,
        descripcion: String,
        tipo: TipoNota,
        fechaLimite: Long? = null
    ) = viewModelScope.launch {
        try {
            val nota = Nota(
                id = UUID.randomUUID().toString(),
                titulo = titulo,
                descripcion = descripcion,
                fechaCreacion = System.currentTimeMillis(),
                archivosMultimedia = emptyList(),
                tipo = tipo,
                fechaLimite = fechaLimite
            )
            repository.insertNota(nota)
            _uiState.update { it.copy(error = null) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun updateNota(nota: Nota) = viewModelScope.launch {
        try {
            repository.updateNota(nota)
            _uiState.update { it.copy(error = null) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun deleteNota(nota: Nota) = viewModelScope.launch {
        try {
            repository.deleteNota(nota)
            _uiState.update { it.copy(error = null) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun loadNota(id: String) = viewModelScope.launch {
        currentNotaId = id
        try {
            val nota = repository.getNota(id)
            _uiState.update { it.copy(
                currentNota = nota,
                error = null
            )}
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.searchNotas(query)
                .catch { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
                .collect { notas ->
                    _uiState.update { it.copy(filteredNotas = notas) }
                }
        }
    }

    fun addMultimedia(uri: Uri, tipo: TipoArchivo, descripcion: String? = null) = viewModelScope.launch {
        try {
            val nota = _uiState.value.currentNota ?: return@launch
            val nuevoArchivo = ArchivoMultimedia(
                id = UUID.randomUUID().toString(),
                tipo = tipo,
                uri = uri.toString(),
                descripcion = descripcion,
                fechaCreacion = System.currentTimeMillis()
            )
            
            val miniatura = when (tipo) {
                TipoArchivo.IMAGEN -> mediaManager.generarMiniatura(uri)
                TipoArchivo.VIDEO -> mediaManager.generarMiniaturaVideo(uri)
                TipoArchivo.AUDIO -> null // No necesita miniatura
            }
            
            val notaActualizada = nota.copy(
                archivosMultimedia = nota.archivosMultimedia + nuevoArchivo
            )
            repository.updateNota(notaActualizada)
            _uiState.update { it.copy(currentNota = notaActualizada) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun addRecordatorio(fechaHora: Long, mensaje: String? = null) = viewModelScope.launch {
        try {
            val nota = _uiState.value.currentNota ?: return@launch
            val nuevoRecordatorio = Recordatorio(
                id = UUID.randomUUID().toString(),
                fechaHora = fechaHora,
                notificada = false
            )
            val notaActualizada = nota.copy(
                recordatorios = nota.recordatorios + nuevoRecordatorio
            )
            repository.updateNota(notaActualizada)
            _uiState.update { it.copy(currentNota = notaActualizada) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun marcarTareaComoCompletada(completada: Boolean = true) = viewModelScope.launch {
        val nota = _uiState.value.currentNota ?: return@launch
        if (nota.tipo != TipoNota.TAREA) return@launch
        
        try {
            val notaActualizada = nota.copy(completada = completada)
            repository.updateNota(notaActualizada)
            _uiState.update { it.copy(currentNota = notaActualizada) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun posponerTarea(nuevaFechaLimite: Long) = viewModelScope.launch {
        val nota = _uiState.value.currentNota ?: return@launch
        if (nota.tipo != TipoNota.TAREA) return@launch
        
        try {
            val notaActualizada = nota.copy(fechaLimite = nuevaFechaLimite)
            repository.updateNota(notaActualizada)
            _uiState.update { it.copy(currentNota = notaActualizada) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }
}

data class NotasUiState(
    val notas: List<Nota> = emptyList(),
    val filteredNotas: List<Nota> = emptyList(),
    val currentNota: Nota? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
}