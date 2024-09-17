package com.sntsb.mypokedex.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.utils.PokemonUtils

class TipoPagingSource(private val pokemonApi: PokemonApi, private val query: String = "") :
    PagingSource<Int, PokemonDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDTO> {
        try {

            Log.e(TAG, "load: $params")

            val pageNumber = params.key ?: 0
            val offset = pageNumber * params.loadSize

            val response = pokemonApi.getPokemonList(params.loadSize, offset)
            Log.e(TAG, "load: ${response.results.size}")

            val pokemonList = response.results.mapIndexed { index, pokemon ->
                val id = offset + index + 1
                val imageUrl = PokemonUtils.getPokemonImageUrl(id)
                val pokemonDetalhesDTO = pokemonApi.getPokemonById(pokemon.nome)

                PokemonDTO(
                    id, pokemon.nome, imageUrl, pokemonDetalhesDTO?.tipos?.map { type ->
                        val idTipo = type.tipo.url.split("/").let { it[it.size - 2] }
                        val imagem = PokemonUtils.getPokemonTypeImageUrl(idTipo.toIntOrNull() ?: -1)

                        TipoDTO(
                            idTipo.toIntOrNull() ?: -1, type.tipo.nome, imagem
                        )
                    } ?: emptyList()
                )
            }

            return LoadResult.Page(
                data = pokemonList,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (response.results.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            Log.e(TAG, "load: Error: ${e.message}")
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonDTO>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val TAG = "PokemonPagingSource"

    }
}