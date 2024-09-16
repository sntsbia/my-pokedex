package com.sntsb.mypokedex.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.utils.PokemonUtils

class PokemonPagingSource(private val pokemonApi: PokemonApi, private val params: PagingParams) :
    PagingSource<Int, PokemonDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDTO> {
        try {

            Log.e(TAG, "load: key: ${params.key}")
            Log.e(TAG, "load: loadSize: ${params.loadSize}")
            Log.e(TAG, "load: query: ${this.params.filter}")

            val pageNumber = params.key ?: 0
            val offset = pageNumber * params.loadSize

            val pokemonList = if (this.params.filter.isNotEmpty()) {
                val response = pokemonApi.getPokemonById(this.params.filter)

                val pokemonList = ArrayList<PokemonDTO>()

                response?.let { pokemonDetalhesResponse ->
                    val imageUrl =
                        PokemonUtils.getPokemonImageUrl(pokemonDetalhesResponse.id) // Função para obter a URL da imagem
                    val dto = PokemonDTO(pokemonDetalhesResponse.id,
                        pokemonDetalhesResponse.name,
                        imageUrl,
                        pokemonDetalhesResponse.types.map { type ->
                            val idTipo = type.tipo.url.split("/").let { it[it.size - 2] }
                            val imagem =
                                PokemonUtils.getPokemonTypeImageUrl(idTipo.toIntOrNull() ?: -1)

                            TipoDTO(
                                idTipo.toIntOrNull() ?: -1, type.tipo.name, imagem
                            )
                        })
                    pokemonList.add(dto)
                }

                pokemonList

            } else {
                val response = pokemonApi.getPokemonList(params.loadSize, offset)
                Log.e(TAG, "load: ${response.results.size}")

                response.results.mapIndexed { index, pokemon ->

                    val id = offset + index + 1

                    val pokemonDetalhesDTO = pokemonApi.getPokemonById(pokemon.name)

                    val imageUrl =
                        PokemonUtils.getPokemonImageUrl(id)
                    PokemonDTO(id, pokemon.name, imageUrl, pokemonDetalhesDTO?.types?.map { type ->
                        val idTipo = type.tipo.url.split("/").let { it[it.size - 2] }
                        val imagem = PokemonUtils.getPokemonTypeImageUrl(idTipo.toIntOrNull() ?: -1)

                        TipoDTO(
                            idTipo.toIntOrNull() ?: -1, type.tipo.name, imagem
                        )
                    } ?: emptyList())
                }
            }

            Log.e(TAG, "load: ${pokemonList.size}")
            return LoadResult.Page(
                data = pokemonList, prevKey = if (pokemonList.size > 1) {
                    if (pageNumber == 0) null else pageNumber - 1
                } else {
                    null
                }, nextKey = if (pokemonList.size > 1) {
                    pageNumber + 1
                } else {
                    null
                }
            )
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) {
                return LoadResult.Page(
                    data = emptyList(), prevKey = null, nextKey = null
                )
            } else {
                Log.e(TAG, "load: Error: ${e.message}")
                e.printStackTrace()
                return LoadResult.Error(e)
            }
        }
        catch (e: Exception) {
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