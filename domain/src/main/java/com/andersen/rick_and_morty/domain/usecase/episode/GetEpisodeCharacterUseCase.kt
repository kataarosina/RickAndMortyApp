package com.andersen.rick_and_morty.domain.usecase.episode


import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository
import com.andersen.rick_and_morty.domain.repository.EpisodeLocalRepository
import com.andersen.rick_and_morty.domain.repository.EpisodeRemoteRepository
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository
import com.andersen.rick_and_morty.domain.repository.LocationRemoteRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetEpisodeCharacterUseCase(
    private val episodeRemoteRepository: EpisodeRemoteRepository,
    private val episodeLocalRepository: EpisodeLocalRepository
) {

    operator fun invoke(charactersList: List<Int>) = flow {
        val characters =
            episodeRemoteRepository.getCharacters(charactersList).fold(onSuccess = { characters ->
                    LceState.Content(characters)
                }, onFailure = { err ->
                    LceState.Error(err)
                })
        emit(characters)
    }.onStart {
        val characters = episodeLocalRepository.getEpisodeCharactersFromDao(charactersList)
        emit(LceState.Content(characters))

    }

}