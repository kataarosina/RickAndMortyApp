package com.andersen.rick_and_morty.domain.usecase.episode

import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.repository.EpisodeLocalRepository
import com.andersen.rick_and_morty.domain.repository.EpisodeRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.flow.onStart

class GetEpisodeUseCase(
    private val episodeRemoteRepository: EpisodeRemoteRepository,
    private val episodeLocalRepository: EpisodeLocalRepository,
) {
    operator fun invoke(episodeId: Int): Flow<LceState<Episode>> = flow {
        val episode = episodeRemoteRepository.getEpisode(episodeId).fold(onSuccess = { episode ->
                LceState.Content(episode)
            }, onFailure = { err ->
                LceState.Error(err)
            })
        emit(episode)

    }.onStart {
        emit(LceState.Content(episodeLocalRepository.getEpisodeFromDao(episodeId)))
    }
}