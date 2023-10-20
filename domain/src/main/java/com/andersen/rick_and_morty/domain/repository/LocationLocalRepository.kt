package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.model.LocationFilters


interface LocationLocalRepository {

    suspend fun getLocationFromDao(id: Int): Location?

    suspend fun insertLocations(locations: List<Location>)

    suspend fun getLocationsFromDao(filter: LocationFilters): List<Location>

    suspend fun getLocationResidentsFromDao(listIds: List<Int>): List<Character>


}