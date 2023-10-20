package com.andersen.rick_and_morty.domain.usecase.location


import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository
import com.andersen.rick_and_morty.domain.repository.LocationRemoteRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetLocationResidentUseCase(
    private val locationRemoteRepository: LocationRemoteRepository,
    private val locationLocalRepository: LocationLocalRepository
) {

    operator fun invoke(residentsList: List<Int>) = flow {
        val residents =
            locationRemoteRepository.getResidents(residentsList).fold(onSuccess = { residents ->
                    LceState.Content(residents)
                }, onFailure = { err ->
                    LceState.Error(err)
                })
        emit(residents)
    }.onStart {
        emit(LceState.Content(locationLocalRepository.getLocationResidentsFromDao(residentsList)))

    }

}