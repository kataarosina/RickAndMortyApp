package com.andersen.rick_and_morty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andersen.rick_and_morty.data.mapper.StringListConverter
import com.andersen.rick_and_morty.data.model.CharacterEntity
import com.andersen.rick_and_morty.data.model.EpisodeEntity
import com.andersen.rick_and_morty.data.model.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
internal abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao
}