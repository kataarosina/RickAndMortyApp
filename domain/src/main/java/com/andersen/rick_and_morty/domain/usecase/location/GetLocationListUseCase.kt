package com.andersen.rick_and_morty.domain.usecase.location

import com.andersen.rick_and_morty.domain.model.Location
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.andersen.rick_and_morty.domain.repository.LocationRemoteRepository

class GetLocationListUseCase(
    private val locationRemoteRepository: LocationRemoteRepository,
) {
    private var pageSize = 50

    suspend operator fun invoke(
        filterQuery: LocationFilters,
        currentPage: Int
    ): Result<List<Location>> {
        return locationRemoteRepository.getLocations(
            currentPage,
            pageSize,
            filterQuery.type,
            filterQuery.dimension,
        )
    }
}