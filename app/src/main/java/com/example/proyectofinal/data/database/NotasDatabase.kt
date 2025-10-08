package com.example.proyectofinal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectofinal.data.database.dao.*
import com.example.proyectofinal.data.database.entities.*

@Database(
    entities = [
        NotaEntity::class,
        ArchivoMultimediaEntity::class,
        RecordatorioEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NotasDatabase : RoomDatabase() {
    abstract fun notaDao(): NotaDao
    abstract fun archivoMultimediaDao(): ArchivoMultimediaDao
    abstract fun recordatorioDao(): RecordatorioDao

    companion object {
        @Volatile
        private var INSTANCE: NotasDatabase? = null

        fun getDatabase(context: Context): NotasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDatabase::class.java,
                    "notas_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}