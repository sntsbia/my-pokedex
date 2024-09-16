package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("base_stat") val valorBase: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val estatistica: EspecificacaoResponse

)