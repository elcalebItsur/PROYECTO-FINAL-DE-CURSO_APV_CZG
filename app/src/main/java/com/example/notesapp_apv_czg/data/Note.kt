package com.example.notesapp_apv_czg.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "notes")
@TypeConverters(Converters::class)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String? = null,
    val isTask: Boolean = false,
    val dueDateMillis: Long? = null,
    val reminderMillis: Long? = null,
    val completed: Boolean = false,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val attachmentUris: List<String> = emptyList(),
    val attachmentDescriptions: List<String> = emptyList()
)
