package com.sntsb.mypokedex.data.api.response

import com.google.gson.annotations.SerializedName

data class TipoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<EspecificacaoResponse>
) {
    override fun toString(): String {
        return "TipoResponse(count=$count, next=$next, previous=$previous, results=$results)"
    }

}