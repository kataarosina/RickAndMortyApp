package com.andersen.rick_and_morty.data.repository

import com.andersen.rick_and_morty.data.database.RickAndMortyDatabase
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository


internal class LocationLocalRepositoryImpl(
    private val locationDatabase: RickAndMortyDatabase,

    ) : LocationLocalRepository {
    override suspend fun getLocationFromDao(id: Int): Location? {
        return locationDatabase.locationDao().getLocation(id)?.toDomainModels()
    }

    override suspend fun insertLocations(locations: List<Location>) {
        locationDatabase.locationDao().insert(locations.map { it.toDomainModels() })
    }

    override suspend fun getLocationsFromDao(filter: LocationFilters): List<Location> {
        return locationDatabase.locationDao().getLocations().map { it.toDomainModels() }
    }

    override suspend fun getLocationResidentsFromDao(listIds: List<Int>): List<Character> {
        return locationDatabase.characterDao().getCharactersByListId(listIds)
            .map { it.toDomainModels() }
    }
}
