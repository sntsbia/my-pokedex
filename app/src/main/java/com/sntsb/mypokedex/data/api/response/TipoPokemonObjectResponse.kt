package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class TipoPokemonObjectResponse(
    @SerializedName("pokemon") val pokemon: EspecificacaoResponse
)