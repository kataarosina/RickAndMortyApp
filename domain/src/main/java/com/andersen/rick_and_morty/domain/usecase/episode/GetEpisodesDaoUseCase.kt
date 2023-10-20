package com.andersen.rick_and_morty.domain.usecase.episode

import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.andersen.rick_and_morty.domain.repository.EpisodeLocalRepository

class GetEpisodesDaoUseCase(private val episodeLocalRepository: EpisodeLocalRepository) {
    suspend operator fun invoke(filter: EpisodeFilters): List<Episode> {
        return episodeLocalRepository.getEpisodesFromDao(filter)
    }
}