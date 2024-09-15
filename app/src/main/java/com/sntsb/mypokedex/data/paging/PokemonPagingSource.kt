package com.sntsb.mypokedex.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.utils.PokemonUtils

class PokemonPagingSource(private val pokemonApi: PokemonApi) : PagingSource<Int, PokemonDTO>() {

    var query = ""

    fun updateQuery(query: String) {
        this.query = query
        invalidate()
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDTO> {
        try {

            Log.e(TAG, "load: key: ${params.key}")
            Log.e(TAG, "load: loadSize: ${params.loadSize}")
            Log.e(TAG, "load: query: ${query}")

            val pageNumber = params.key ?: 0
            val offset = pageNumber * params.loadSize

            val response = pokemonApi.getPokemonList(params.loadSize, offset)
            Log.e(TAG, "load: ${response.results.size}")

            val pokemonList = response.results.mapIndexed { index, pokemon ->
                val id = offset + index + 1
                val imageUrl = PokemonUtils.getPokemonImageUrl(id) // Função para obter a URL da imagem
                PokemonDTO(
                    id, pokemon.name, imageUrl
                )
            }

            return LoadResult.Page(
                data = pokemonList,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (pokemonList.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            Log.e(TAG, "load: Error: ${e.message}")
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val TAG = "PokemonPagingSource"

    }
}