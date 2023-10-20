package com.andersen.rick_and_morty.domain.usecase.character


import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCharacterEpisodeUseCase(
    private val characterRemoteRepository: CharacterRemoteRepository,
    private val characterLocalRepository: CharacterLocalRepository
) {
    operator fun invoke(episodesList: List<Int>) = flow {
        val episodes =
            characterRemoteRepository.getEpisodes(episodesList).fold(onSuccess = { episodes ->
                    LceState.Content(episodes)
                }, onFailure = { err ->
                    LceState.Error(err)
                })
        emit(episodes)
    }.onStart {
        emit(LceState.Content(characterLocalRepository.getCharacterEpisodesFromDao(episodesList)))
    }

}