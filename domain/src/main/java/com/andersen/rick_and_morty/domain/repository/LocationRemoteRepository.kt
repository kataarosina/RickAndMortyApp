package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Location

interface LocationRemoteRepository {

    suspend fun getLocation(id: Int): Result<Location>

    suspend fun getLocations(
        page: Int,
        pageSize: Int,
        type: String,
        dimension: String
    ): Result<List<Location>>

    suspend fun getResidents(residentUrls: List<Int>): Result<List<Character>>

}