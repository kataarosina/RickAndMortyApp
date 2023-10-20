package com.andersen.rick_and_morty.data.koin

import androidx.room.Room
import com.andersen.rick_and_morty.data.database.RickAndMortyDatabase
import com.andersen.rick_and_morty.data.mapper.StringListConverter
import org.koin.dsl.module

internal val rickAndMortyDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(), RickAndMortyDatabase::class.java, "rick_and_morty_database"
        ).fallbackToDestructiveMigration().build()
    }
}