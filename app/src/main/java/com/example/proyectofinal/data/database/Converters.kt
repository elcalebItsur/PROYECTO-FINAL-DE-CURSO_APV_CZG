package com.example.proyectofinal.data.database

import androidx.room.TypeConverter
import com.example.proyectofinal.data.model.TipoNota

class Converters {
    @TypeConverter
    fun toTipoNota(value: String) = enumValueOf<TipoNota>(value)

    @TypeConverter
    fun fromTipoNota(value: TipoNota) = value.name
}