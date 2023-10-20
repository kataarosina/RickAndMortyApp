package com.andersen.rick_and_morty.ui.locationDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.usecase.location.GetLocationResidentUseCase
import com.andersen.rick_and_morty.domain.usecase.location.GetLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailLocationViewModel(
    getLocationUseCase: GetLocationUseCase,
    private val getLocationResidentUseCase: GetLocationResidentUseCase,
    locationId: Int,
) : ViewModel() {

    val locationFlow = getLocationUseCase.invoke(locationId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )
    private val locationResidents = MutableStateFlow<LceState<List<Character>>>(LceState.Loading)
    val locationResidentsFlow: StateFlow<LceState<List<Character>>>
        get() = locationResidents

    fun getlocationResidents(residentsIds: List<Int>) {
        locationResidents.value = LceState.Loading
        viewModelScope.launch {
            try {
                val residents = getLocationResidentUseCase(residentsIds)
                residents.collect { residents ->
                    locationResidents.value = residents
                }
            } catch (e: Exception) {
                locationResidents.value = LceState.Error(e)
            }
        }
    }


}
