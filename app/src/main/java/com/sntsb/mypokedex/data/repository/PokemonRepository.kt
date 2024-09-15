package com.sntsb.mypokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.model.PokemonModel
import com.sntsb.mypokedex.model.dto.PokemonDTO
import javax.inject.Inject

/** Classe que define o repositório de dados do aplicativo (faz chamadas para API) **/
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) {

    fun restart(): Pager<Int, PokemonDTO> {
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(pokemonApi) })
    }

    fun getPokemonPager(): Pager<Int, PokemonDTO> {
        return Pager(config = PagingConfig(pageSize = PokemonApi.LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(pokemonApi) })
    }

    suspend fun getPokemon(id: Int): PokemonModel? {


        return null
    }

    companion object

}