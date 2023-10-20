package com.andersen.rick_and_morty.ui.locationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.andersen.rick_and_morty.domain.usecase.location.GetLocationListUseCase
import com.andersen.rick_and_morty.domain.usecase.location.GetLocationsDaoUseCase
import com.andersen.rick_and_morty.domain.usecase.location.SaveLocationUseCase
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

class ListLocationViewModel(
    private val getLocationListUseCase: GetLocationListUseCase,
    private val getLocationsDaoUseCase: GetLocationsDaoUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
) : ViewModel() {

    private val filter = LocationFilters("", "", "")
    private val filterQuery = MutableStateFlow(filter)


    private var currentPage = 1
    private var isLoading = false

    private val loadLocationsFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val mutableState = MutableStateFlow<LceState<List<Location>>>(LceState.Loading)
    val state: StateFlow<LceState<List<Location>>>
        get() = mutableState

    val locationsFlow = filterQuery.onEach {
            currentPage = 1
            isLoading = false
        }.flatMapLatest { query ->
            locationDataFlow(query)
        }.onStart {
            onLoadLocations()

        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadLocations() {
        loadLocationsFlow.tryEmit(Unit)
    }

    fun onFilterChanged(filter: LocationFilters) {
        filterQuery.value = filter
    }

    fun getFilter(): LocationFilters {
        return filterQuery.value
    }

    private fun locationDataFlow(filterQuery: LocationFilters): Flow<List<Location>> {
        return loadLocationsFlow.filter { !isLoading }.onEach {
                isLoading = true
                mutableState.value = LceState.Loading
            }.map {
                getLocationListUseCase.invoke(filterQuery, currentPage)
                    .apply { isLoading = false }
                    .fold(onSuccess = {
                        saveLocationUseCase.invoke(it)
                        currentPage++
                        val filteredData = it.filter { location ->
                            val nameMatches = location.name!!.contains(
                                filterQuery.namePrefix,
                                ignoreCase = true
                            )

                            nameMatches
                        }
                        mutableState.value = LceState.Content(filteredData)
                        filteredData

                    }, onFailure = {
                        mutableState.value = LceState.Error(it)
                        emptyList()

                    })
            }.onStart {
                val filteredData = getLocationsDaoUseCase.invoke(filterQuery).filter { location ->
                    val nameMatches =
                        location.name!!.contains(filterQuery.namePrefix, ignoreCase = true)
                    nameMatches
                }
                emit(filteredData)

            }.runningReduce { accumulator, value ->
                accumulator + value
            }.scan(emptyList<Location>()) { accumulator, value ->
                val newElements = value.filter { newValue ->
                    !accumulator.any { it.id == newValue.id }
                }
                accumulator + newElements
            }.distinctUntilChanged()


    }

    fun setSearchQuery(query: String) {
        val currentFilter = filterQuery.value
        filterQuery.value = currentFilter.copy(namePrefix = query)
    }
}
