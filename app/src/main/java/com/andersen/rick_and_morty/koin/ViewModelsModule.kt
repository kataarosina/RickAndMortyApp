package com.andersen.rick_and_morty.koin

import com.andersen.rick_and_morty.ui.characterDetail.DetailCharacterViewModel
import com.andersen.rick_and_morty.ui.characterList.ListCharacterViewModel
import com.andersen.rick_and_morty.ui.episodeDetail.DetailEpisodeViewModel
import com.andersen.rick_and_morty.ui.episodeList.ListEpisodeViewModel
import com.andersen.rick_and_morty.ui.locationDetail.DetailLocationViewModel
import com.andersen.rick_and_morty.ui.locationList.ListLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {

    viewModelOf(::ListLocationViewModel)
    viewModelOf(::DetailLocationViewModel)
    viewModelOf(::ListCharacterViewModel)
    viewModelOf(::DetailCharacterViewModel)
    viewModelOf(::ListEpisodeViewModel)
    viewModelOf(::DetailEpisodeViewModel)

}