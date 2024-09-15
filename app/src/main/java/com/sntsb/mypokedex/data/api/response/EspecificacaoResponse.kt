package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class EspecificacaoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)