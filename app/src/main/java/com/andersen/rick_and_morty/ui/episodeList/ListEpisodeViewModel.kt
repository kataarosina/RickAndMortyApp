package com.andersen.rick_and_morty.ui.episodeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.usecase.episode.GetEpisodeListUseCase
import com.andersen.rick_and_morty.domain.usecase.episode.GetEpisodesDaoUseCase
import com.andersen.rick_and_morty.domain.usecase.episode.SaveEpisodeUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn

class ListEpisodeViewModel(
    private val getEpisodeListUseCase: GetEpisodeListUseCase,
    private val getEpisodesDaoUseCase: GetEpisodesDaoUseCase,
    private val saveEpisodeUseCase: SaveEpisodeUseCase,
) : ViewModel() {

    private val filter = EpisodeFilters("", "", "")
    private val filterQuery = MutableStateFlow(filter)


    private var currentPage = 1
    private var isLoading = false

    private val loadEpisodesFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val mutableState = MutableStateFlow<LceState<List<Episode>>>(LceState.Loading)
    val state: StateFlow<LceState<List<Episode>>>
        get() = mutableState

    val episodesFlow = filterQuery
        .onEach {
            currentPage = 1
            isLoading = false
        }
        .flatMapLatest { query ->
            episodeDataFlow(query)
        }
        .onStart {
            onLoadEpisodes()
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadEpisodes() {
        loadEpisodesFlow.tryEmit(Unit)
    }

    fun onFilterChanged(filter: EpisodeFilters) {
        filterQuery.value = filter
    }

    fun getFilter(): EpisodeFilters {
        return filterQuery.value
    }

    private fun episodeDataFlow(filterQuery: EpisodeFilters): Flow<List<Episode>> {
        return loadEpisodesFlow
            .filter { !isLoading }
            .onEach {
                isLoading = true
                mutableState.value = LceState.Loading
            }
            .map {
                getEpisodeListUseCase.invoke(filterQuery, currentPage)
                    .apply { isLoading = false }
                    .fold(
                        onSuccess = {
                            saveEpisodeUseCase.invoke(it)
                            currentPage++
                            val filteredData = it.filter { episode ->
                                val nameMatches = episode.name!!.contains(
                                    filterQuery.namePrefix,
                                    ignoreCase = true
                                )

                                nameMatches
                            }
                            mutableState.value = LceState.Content(filteredData)
                            filteredData

                        },
                        onFailure = {
                            mutableState.value = LceState.Error(it)
                            emptyList()
                        }
                    )
            }
            .onStart {
                val filteredData = getEpisodesDaoUseCase.invoke(filterQuery).filter { episode ->
                    val nameMatches =
                        episode.name!!.contains(filterQuery.namePrefix, ignoreCase = true)

                    nameMatches
                }
                emit(filteredData)


            }
            .runningReduce { accumulator, value ->
                accumulator + value
            }
            .scan(emptyList<Episode>()) { accumulator, value ->
                val newElements = value.filter { newValue ->
                    !accumulator.any { it.id == newValue.id }
                }
                accumulator + newElements
            }
            .distinctUntilChanged()


    }

    fun setSearchQuery(query: String) {
        val currentFilter = filterQuery.value
        filterQuery.value = currentFilter.copy(namePrefix = query)
    }
}
