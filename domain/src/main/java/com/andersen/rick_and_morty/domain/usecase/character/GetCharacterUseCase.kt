package com.andersen.rick_and_morty.domain.usecase.character

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetCharacterUseCase(
    private val characterRemoteRepository: CharacterRemoteRepository,
    private val characterLocalRepository: CharacterLocalRepository,
) {

    operator fun invoke(characterId: Int): Flow<LceState<Character>> = flow {

        val character =
            characterRemoteRepository.getCharacter(characterId).fold(onSuccess = { character ->
                    LceState.Content(character)
                }, onFailure = { err ->
                    LceState.Error(err)
                })
        emit(character)

    }.onStart {
        emit(LceState.Content(characterLocalRepository.getCharacterFromDao(characterId)))
    }
}