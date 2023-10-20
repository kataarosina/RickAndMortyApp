package com.andersen.rick_and_morty.domain.usecase.location

import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository

class GetLocationsDaoUseCase(private val locationLocalRepository: LocationLocalRepository) {
    suspend operator fun invoke(filter: LocationFilters): List<Location> {
        return locationLocalRepository.getLocationsFromDao(filter)
    }
}