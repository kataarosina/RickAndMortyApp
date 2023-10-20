package com.andersen.rick_and_morty.ui.episodeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.usecase.episode.GetEpisodeCharacterUseCase
import com.andersen.rick_and_morty.domain.usecase.episode.GetEpisodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailEpisodeViewModel(
    getEpisodeUseCase: GetEpisodeUseCase,
    episodeId: Int,
    private val getEpisodeCharacterUseCase: GetEpisodeCharacterUseCase,
) : ViewModel() {

    val episodeFlow = getEpisodeUseCase.invoke(episodeId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )
    private val episodeCharacters = MutableStateFlow<LceState<List<Character>>>(LceState.Loading)
    val episodeCharactersFlow: StateFlow<LceState<List<Character>>>
        get() = episodeCharacters

    fun getEpisodeCharacters(characterIds: List<Int>) {
        episodeCharacters.value = LceState.Loading
        viewModelScope.launch {
            try {
                val characters = getEpisodeCharacterUseCase(characterIds)
                characters.collect { characters ->
                    episodeCharacters.value = characters
                }
            } catch (e: Exception) {
                episodeCharacters.value = LceState.Error(e)
            }
        }
    }

}
