package com.andersen.rick_and_morty.data.mapper

import com.andersen.rick_and_morty.data.model.LocationDTO
import com.andersen.rick_and_morty.data.model.LocationEntity
import com.andersen.rick_and_morty.domain.model.Location


internal fun List<LocationDTO>.toDomainModels(): List<Location> {
    return map { it.toDomainModels() }
}


internal fun LocationDTO.toDomainModels(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,

        )
}


internal fun Location.toDomainModels(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,

        )
}


internal fun LocationEntity.toDomainModels(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,
    )
}