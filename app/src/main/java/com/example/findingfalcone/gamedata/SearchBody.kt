package com.example.findingfalcone.gamedata

import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    var planet_names: ArrayList<String> = arrayListOf(),
    var vehicle_names: ArrayList<String> = arrayListOf(),
    var token: String = ""
)