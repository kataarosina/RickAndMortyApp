package com.andersen.rick_and_morty.domain.model

import kotlinx.serialization.Serializable

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class ChatacterFilters(
    val species: String,
    val type: String,
    val status: String,
    val gender: String,
    val namePrefix: String
) : java.io.Serializable