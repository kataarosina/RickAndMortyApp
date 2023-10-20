package com.andersen.rick_and_morty.ui.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.usecase.character.GetCharacterEpisodeUseCase
import com.andersen.rick_and_morty.domain.usecase.character.GetCharacterUseCase
import com.andersen.rick_and_morty.domain.usecase.location.GetLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailCharacterViewModel(
    getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterEpisodeUseCase: GetCharacterEpisodeUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    characterId: Int,
) : ViewModel() {

    val characterFlow = getCharacterUseCase.invoke(characterId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )

    private val characterEpisodes = MutableStateFlow<LceState<List<Episode>>>(LceState.Loading)
    val characterEpisodesFlow: StateFlow<LceState<List<Episode>>>
        get() = characterEpisodes

    fun getCharacterEpisodes(episodeIds: List<Int>) {
        characterEpisodes.value = LceState.Loading
        viewModelScope.launch {
            try {
                val episodes = getCharacterEpisodeUseCase(episodeIds)
                episodes.collect { episodes ->
                    characterEpisodes.value = episodes
                }
            } catch (e: Exception) {
                characterEpisodes.value = LceState.Error(e)
            }
        }
    }


    private val locationData = MutableStateFlow<LceState<Location>>(LceState.Loading)

    val locationFlow: StateFlow<LceState<Location>> = locationData

    fun getLocation(locationId: Int) {
        locationData.value = LceState.Loading
        viewModelScope.launch {
            try {
                val location = getLocationUseCase(locationId)
                location.collect {
                    locationData.value = it
                }
            } catch (e: Exception) {
                locationData.value = LceState.Error(e)
            }
        }
    }


    private val originData = MutableStateFlow<LceState<Location>>(LceState.Loading)

    val originFlow: StateFlow<LceState<Location>> = originData

    fun getOrigin(originId: Int) {
        originData.value = LceState.Loading
        viewModelScope.launch {
            try {
                val location = getLocationUseCase(originId)
                location.collect {
                    originData.value = it
                }
            } catch (e: Exception) {
                originData.value = LceState.Error(e)
            }
        }
    }


}
