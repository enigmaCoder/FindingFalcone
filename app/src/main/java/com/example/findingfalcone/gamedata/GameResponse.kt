package com.example.findingfalcone.gamedata

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val planet_name: String? = null,
    val status: String? = null,
    val error: String? = null
)