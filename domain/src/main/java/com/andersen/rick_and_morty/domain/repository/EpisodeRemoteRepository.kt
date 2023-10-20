package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Episode

interface EpisodeRemoteRepository {

    suspend fun getEpisode(id: Int): Result<Episode>

    suspend fun getEpisodes(
        page: Int,
        pageSize: Int,
        episode: String,
        airDate: String
    ): Result<List<Episode>>

    suspend fun getCharacters(residentUrls: List<Int>): Result<List<Character>>

}