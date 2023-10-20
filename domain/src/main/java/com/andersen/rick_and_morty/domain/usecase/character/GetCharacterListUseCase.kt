package com.andersen.rick_and_morty.domain.usecase.character

import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository


class GetCharacterListUseCase(
    private val characterRemoteRepository: CharacterRemoteRepository,
) {
    private var pageSize = 50

    suspend operator fun invoke(
        filterQuery: ChatacterFilters,
        currentPage: Int
    ): Result<List<Character>> {
        return characterRemoteRepository.getCharacters(
            currentPage,
            pageSize,
            filterQuery.species,
            filterQuery.type,
            filterQuery.status,
            filterQuery.gender
        )
    }
}