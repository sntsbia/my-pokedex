package com.sntsb.mypokedex.data.api

import com.sntsb.mypokedex.data.api.response.PokemonDetalhesResponse
import com.sntsb.mypokedex.data.api.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** Interface que define os endpoints da API do aplicativo para POKEMON **/
interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = LIMIT, @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonDetalhesResponse

    companion object {
        const val LIMIT = 20
    }

}

