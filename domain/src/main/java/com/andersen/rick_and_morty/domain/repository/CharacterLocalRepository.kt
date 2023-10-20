package com.andersen.rick_and_morty.domain.repository

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.model.Episode

interface CharacterLocalRepository {
    suspend fun getCharacterFromDao(id: Int): Character

    suspend fun insertCharacters(characters: List<Character>)

    suspend fun getCharactersFromDao(filter: ChatacterFilters): List<Character>

    suspend fun getCharacterEpisodesFromDao(listIds: List<Int>): List<Episode>


}