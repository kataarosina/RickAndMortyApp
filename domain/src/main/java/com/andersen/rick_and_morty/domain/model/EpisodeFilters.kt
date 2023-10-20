package com.andersen.rick_and_morty.domain.model

import kotlinx.serialization.Serializable

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class EpisodeFilters(
    val episode: String, val airDate: String, val namePrefix: String
) : java.io.Serializable