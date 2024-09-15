package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class TipoPokemonResponse(
    @SerializedName("slot") val slot: String,
    @SerializedName("type") val tipo: EspecificacaoResponse
)