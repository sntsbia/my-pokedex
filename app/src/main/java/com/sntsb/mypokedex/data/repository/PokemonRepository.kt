package com.sntsb.mypokedex.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.data.model.dto.ImagemDTO
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.model.dto.PokemonDetalhesDTO
import com.sntsb.mypokedex.data.model.dto.StatusDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.data.paging.PagingParams
import com.sntsb.mypokedex.data.paging.PokemonPagingSource
import com.sntsb.mypokedex.utils.PokemonUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Classe que define o repositório de dados do aplicativo (faz chamadas para API) **/
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) {


    private fun getPagingSource(filtro: String): PagingSource<Int, PokemonDTO> {
        val pagingParams = PagingParams(
            filter = filtro
        )
        return PokemonPagingSource(pokemonApi, pagingParams)
    }

    fun getPager(filtro: String): Flow<PagingData<PokemonDTO>> {
        return Pager(config = PagingConfig(pageSize = PokemonApi.LIMIT),
            pagingSourceFactory = { getPagingSource(filtro) }).flow
    }

    suspend fun getOne(id: String): PokemonDetalhesDTO? {
        try {

            val response = pokemonApi.getPokemonById(id)

            return response?.let { pokemon ->

                Log.e(TAG, "getOne: ${pokemon}")

                val status = pokemon.stats.map { stat ->
                    StatusDTO(stat.stat.name, stat.valorBase)
                }

                val tipos = pokemon.types.map { type ->

                    val idTipo = type.tipo.url.split("/").let { it[it.size - 2] }
                    val imagem = PokemonUtils.getPokemonTypeImageUrl(idTipo.toIntOrNull() ?: -1)
                    TipoDTO(
                        idTipo.toIntOrNull() ?: -1, type.tipo.name, imagem
                    )
                }

                val imagemArray = ArrayList<ImagemDTO>()
                imagemArray.add(
                    ImagemDTO(
                        ImagemDTO.IMAGEM_FRONT,
                        PokemonUtils.getPokemonImageUrl(pokemon.id)
                    )
                )
                imagemArray.add(
                    ImagemDTO(
                        ImagemDTO.IMAGEM_SHINY,
                        PokemonUtils.getPokemonShinyImageUrl(pokemon.id)
                    )
                )

                PokemonDetalhesDTO(
                    pokemon.id,
                    pokemon.name,
                    imagemArray,
                    pokemon.height,
                    pokemon.weight,
                    tipos,
                    status
                )
            }

        } catch (e: Exception) {
            Log.e(TAG, "getOne: Error: ${e.message}")
            e.printStackTrace()
            return null

        }

    }

    companion object {
        private const val TAG = "PokemonRepository"

    }

}