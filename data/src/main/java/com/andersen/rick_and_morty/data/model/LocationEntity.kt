package com.andersen.rick_and_morty.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andersen.rick_and_morty.data.mapper.StringListConverter

@Entity(tableName = "location_table")
internal data class LocationEntity(
    @PrimaryKey @NonNull @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "dimension") val dimension: String?,
    @TypeConverters(StringListConverter::class) @ColumnInfo(name = "residents") val residents: List<String>?
)