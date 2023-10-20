package com.andersen.rick_and_morty.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andersen.rick_and_morty.data.mapper.StringListConverter


@Entity(tableName = "character_table")
internal data class CharacterEntity(
    @PrimaryKey @NonNull @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "species") val species: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "origin") val origin: String?,
    @TypeConverters(StringListConverter::class) @ColumnInfo(name = "episodes") val episodes: List<String>?
)