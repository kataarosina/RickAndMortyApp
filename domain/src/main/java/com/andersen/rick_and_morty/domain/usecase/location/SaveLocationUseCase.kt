package com.andersen.rick_and_morty.domain.usecase.location

import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.repository.LocationLocalRepository


class SaveLocationUseCase(private val locationLocalRepository: LocationLocalRepository) {

    suspend operator fun invoke(listLocations: List<Location>) {
        locationLocalRepository.insertLocations(listLocations)
    }

}
