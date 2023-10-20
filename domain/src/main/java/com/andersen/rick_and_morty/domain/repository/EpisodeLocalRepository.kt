package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.EpisodeFilters


interface EpisodeLocalRepository {

    suspend fun getEpisodeFromDao(id: Int): Episode

    suspend fun insertEpisodes(episodes: List<Episode>)

    suspend fun getEpisodesFromDao(filters: EpisodeFilters): List<Episode>

    suspend fun getEpisodeCharactersFromDao(listIds: List<Int>): List<Character>

}