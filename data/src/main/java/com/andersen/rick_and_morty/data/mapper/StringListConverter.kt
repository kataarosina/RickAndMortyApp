package com.andersen.rick_and_morty.data.mapper

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

internal class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun toString(value: List<String>?): String {
        return value?.joinToString(",") ?: ""
    }
}