package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonTipoResponse(
    @SerializedName("pokemon") val pokemon: EspecificacaoResponse
)