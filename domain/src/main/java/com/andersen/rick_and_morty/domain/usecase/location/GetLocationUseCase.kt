package com.andersen.rick_and_morty.domain.usecase.location

import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository
import com.andersen.rick_and_morty.domain.repository.LocationRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.flow.onStart
import java.lang.Exception

class GetLocationUseCase(
    private val locationRemoteRepository: LocationRemoteRepository,
    private val locationLocalRepository: LocationLocalRepository,
) {

    operator fun invoke(locationId: Int): Flow<LceState<Location>> = flow {
        val location =
            locationRemoteRepository.getLocation(locationId).fold(onSuccess = { location ->
                    LceState.Content(location)
                }, onFailure = { err ->
                    LceState.Error(err)
                })
        emit(location)

    }.onStart {
        locationLocalRepository.getLocationFromDao(locationId)?.let {
            emit(LceState.Content(it))
        }
    }
}