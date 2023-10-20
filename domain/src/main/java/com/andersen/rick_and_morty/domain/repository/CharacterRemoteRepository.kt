package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Episode

interface CharacterRemoteRepository {

    suspend fun getCharacter(id: Int): Result<Character>

    suspend fun getCharacters(
        page: Int,
        pageSize: Int,
        species: String,
        type: String,
        status: String,
        gender: String
    ): Result<List<Character>>

    suspend fun getEpisodes(episodeUrls: List<Int>): Result<List<Episode>>


}