package com.andersen.rick_and_morty.data.repository


import android.util.Log
import com.andersen.rick_and_morty.data.api.RickandMortyApi
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.repository.LocationRemoteRepository
import com.andersen.rick_and_morty.domain.model.Location

internal class LocationRemoteRepositoryImpl(
    private val locationService: RickandMortyApi,
) : LocationRemoteRepository {

    override suspend fun getLocation(id: Int): Result<Location> {
        return runCatching {
            locationService.getLocation(id)

        }.map {
            it.toDomainModels()
        }
    }

    override suspend fun getLocations(
        page: Int, pageSize: Int, type: String, dimension: String
    ): Result<List<Location>> {
        return runCatching {

            locationService.getLocations(page, pageSize, type, dimension).results
        }.map { it.toDomainModels() }
    }

    override suspend fun getResidents(residentUrls: List<Int>): Result<List<Character>> {
        return runCatching {
            val residents = mutableListOf<Character>()
            residentUrls.forEach { residentId ->
                val resident = locationService.getCharacter(residentId)
                residents.add(resident.toDomainModels())
            }
            residents
        }
    }


}