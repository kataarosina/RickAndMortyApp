package com.andersen.rick_and_morty.data.model

import com.google.gson.annotations.SerializedName

internal data class EpisodesDTO(
    val results: List<EpisodeDTO>,
)

internal data class EpisodeDTO(
    val id: Int,
    val name: String?,
    val episode: String?,
    @SerializedName("air_date") val airDate: String?,
    val characters: List<String>?,
)