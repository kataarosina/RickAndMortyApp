package com.andersen.rick_and_morty.data.model

internal data class LocationsDTO(
    val results: List<LocationDTO>,
)

internal data class LocationDTO(
    val id: Int,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<String>?
)
