package com.example.proyectofinal.di

import android.content.Context
import com.example.proyectofinal.data.database.NotasDatabase
import com.example.proyectofinal.data.database.dao.ArchivoMultimediaDao
import com.example.proyectofinal.data.database.dao.NotaDao
import com.example.proyectofinal.data.database.dao.RecordatorioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideNotasDatabase(
        @ApplicationContext context: Context
    ): NotasDatabase = NotasDatabase.getDatabase(context)
    
    @Provides
    fun provideNotaDao(database: NotasDatabase): NotaDao = database.notaDao()
    
    @Provides
    fun provideArchivoMultimediaDao(database: NotasDatabase): ArchivoMultimediaDao = 
        database.archivoMultimediaDao()
    
    @Provides
    fun provideRecordatorioDao(database: NotasDatabase): RecordatorioDao = 
        database.recordatorioDao()
}