package com.andersen.rick_and_morty.domain.usecase.episode

import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.repository.EpisodeLocalRepository


class SaveEpisodeUseCase(private val episodeLocalRepository: EpisodeLocalRepository) {

    suspend operator fun invoke(listEpisodes: List<Episode>) {
        episodeLocalRepository.insertEpisodes(listEpisodes)
    }

}
