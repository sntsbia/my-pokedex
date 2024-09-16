package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonDetalhesResponse(
    @SerializedName("height") val altura: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nome: String,
    @SerializedName("weight") val peso: Int,
    @SerializedName("stats") val estatisticas: List<StatusResponse>,
    @SerializedName("types") val tipos: List<PokemonTipoObjectReponse>,
)