package com.example.findingfalcone.gamedata

import kotlinx.serialization.Serializable

@Serializable
data class PlanetsItem(
    val distance: Int,
    val name: String
)