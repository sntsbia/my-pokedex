package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class TipoDetalhesResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pokemon") val pokemon: List<PokemonTipoResponse>
)