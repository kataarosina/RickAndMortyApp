package com.andersen.rick_and_morty.domain.usecase.episode

import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.andersen.rick_and_morty.domain.repository.EpisodeRemoteRepository

class GetEpisodeListUseCase(
    private val episodeRemoteRepository: EpisodeRemoteRepository,
) {
    private var pageSize = 50

    suspend operator fun invoke(
        filterQuery: EpisodeFilters,
        currentPage: Int
    ): Result<List<Episode>> {
        return episodeRemoteRepository.getEpisodes(
            currentPage, pageSize, filterQuery.episode, filterQuery.airDate
        )
    }
}