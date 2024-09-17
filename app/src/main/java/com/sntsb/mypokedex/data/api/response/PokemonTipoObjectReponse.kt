package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class PokemonTipoObjectReponse(
    @SerializedName("type") val tipo: EspecificacaoResponse
)