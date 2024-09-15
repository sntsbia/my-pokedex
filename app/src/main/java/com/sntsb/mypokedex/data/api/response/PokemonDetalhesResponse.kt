package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonDetalhesResponse(
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("stats") val stats: List<StatusResponse>,
    @SerializedName("types") val types: List<TipoPokemonResponse>,
)