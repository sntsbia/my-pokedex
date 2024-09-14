package com.sntsb.mypokedex.data

import com.sntsb.mypokedex.model.PokemonDetalhesModel
import com.sntsb.mypokedex.model.PokemonImageModel
import com.sntsb.mypokedex.model.PokemonModel
import com.sntsb.mypokedex.model.TipoModel
import retrofit2.http.GET
import retrofit2.http.Path

/** Interface que define os endpoints da API do aplicativo **/
interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): List<PokemonModel>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetalhesModel

    @GET("pokemon/{id}/")
    suspend fun getPokemonImages(@Path("id") id: Int): List<PokemonImageModel>

    @GET("type")
    suspend fun getTipos(): List<TipoModel>

    @GET("pokemon?limit={limit}&offset={offset}}")
    suspend fun getPokemonsPaginado(@Path("limit") limit: Int = LIMIT, @Path("offset") offset: Int): List<PokemonModel>

    companion object {
        const val LIMIT = 20
    }

}