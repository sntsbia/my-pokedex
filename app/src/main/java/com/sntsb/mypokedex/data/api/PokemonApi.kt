package com.sntsb.mypokedex.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

/** Interface que define os endpoints da API do aplicativo **/
interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = LIMIT, @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonById(@Query("id") id: Int): PokemonDetalhesResponse

    @GET("type")
    suspend fun getTipos(): TipoResponse

    companion object {
        const val LIMIT = 20
    }

    data class Especificacao(
        @SerializedName("name") val name: String, @SerializedName("url") val url: String
    )

    data class PokemonResponse(
        @SerializedName("count") val count: Int,
        @SerializedName("next") val next: String?,
        @SerializedName("previous") val previous: String?,
        @SerializedName("results") val results: List<Especificacao>
    )

    data class TipoResponse(
        @SerializedName("count") val count: Int,
        @SerializedName("next") val next: String?,
        @SerializedName("previous") val previous: String?,
        @SerializedName("results") val results: List<Tipo>
    )

    data class PokemonDetalhesResponse(
        @SerializedName("height") val height: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("weight") val weight: Int,
        @SerializedName("stats") val stats: List<Status>,
        @SerializedName("types") val types: List<Especificacao>,
    )

    data class Status(
        @SerializedName("base_stat") val valorBase: Int,
        @SerializedName("effort") val effort: Int,
        @SerializedName("stat") val stat: Especificacao

    )

    data class Tipo(
        @SerializedName("slot") val slot: String,
        @SerializedName("type") val tipos: List<Especificacao>
    )

}

