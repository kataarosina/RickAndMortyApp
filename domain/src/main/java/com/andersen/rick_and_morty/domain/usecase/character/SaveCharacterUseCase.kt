package com.andersen.rick_and_morty.domain.usecase.character

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository

class SaveCharacterUseCase(private val characterLocalRepository: CharacterLocalRepository) {

    suspend operator fun invoke(listCharacters: List<Character>) {
        characterLocalRepository.insertCharacters(listCharacters)
    }

}
