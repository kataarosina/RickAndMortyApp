package com.andersen.rick_and_morty.domain.usecase.character

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository

class GetCharactersDaoUseCase(private val characterLocalRepository: CharacterLocalRepository) {
    suspend operator fun invoke(filter: ChatacterFilters): List<Character> {
        return characterLocalRepository.getCharactersFromDao(filter)
    }
}