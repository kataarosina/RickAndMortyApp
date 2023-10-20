package com.andersen.rick_and_morty.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.andersen.rick_and_morty.data.mapper.StringListConverter

@Entity(tableName = "episode_table")
internal data class EpisodeEntity(
    @PrimaryKey @NonNull @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "episode") val episode: String?,
    @ColumnInfo(name = "air_date") val airDate: String?,
    @TypeConverters(StringListConverter::class) @ColumnInfo(name = "characters") val characters: List<String>?

)