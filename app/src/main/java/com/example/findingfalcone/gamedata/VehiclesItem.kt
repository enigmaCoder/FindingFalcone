package com.example.findingfalcone.gamedata

import kotlinx.serialization.Serializable

@Serializable
data class VehiclesItem(
    val max_distance: Int,
    val name: String,
    val speed: Int,
    val total_no: Int
)