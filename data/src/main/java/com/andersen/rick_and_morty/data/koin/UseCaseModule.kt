package com.andersen.rick_and_morty.data.koin

import com.andersen.rick_and_morty.domain.usecase.location.*
import com.andersen.rick_and_morty.domain.usecase.episode.*
import com.andersen.rick_and_morty.domain.usecase.character.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


internal val useCaseModule = module {
    factoryOf(::GetCharacterUseCase)
    factoryOf(::GetCharacterListUseCase)
    factoryOf(::GetCharactersDaoUseCase)
    factoryOf(::SaveCharacterUseCase)
    factoryOf(::GetCharacterEpisodeUseCase)

    factoryOf(::GetLocationUseCase)
    factoryOf(::GetLocationListUseCase)
    factoryOf(::GetLocationsDaoUseCase)
    factoryOf(::SaveLocationUseCase)
    factoryOf(::GetLocationResidentUseCase)

    factoryOf(::GetEpisodeUseCase)
    factoryOf(::GetEpisodeListUseCase)
    factoryOf(::GetEpisodesDaoUseCase)
    factoryOf(::SaveEpisodeUseCase)
    factoryOf(::GetEpisodeCharacterUseCase)







}