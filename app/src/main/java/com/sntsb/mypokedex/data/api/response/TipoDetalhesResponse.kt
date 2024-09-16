package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class TipoDetalhesResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nome: String,
    @SerializedName("pokemon") val pokemons: List<TipoPokemonObjectResponse>
)