package com.sntsb.mypokedex.data.repository

import android.util.Log
import com.sntsb.mypokedex.data.api.PokemonApi
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.utils.PokemonUtils
import javax.inject.Inject

/** Classe que define o repositório de dados do aplicativo (faz chamadas para API) **/
class TipoRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
) {

    suspend fun getTipos(): ArrayList<TipoDTO> {
        val tipos = ArrayList<TipoDTO>()

        try {

            val response = pokemonApi.getTipos()

            Log.e(TAG, "getTipos: $response")

            response.let { resposta ->
                resposta.results.forEach { tipo ->
                    Log.e(TAG, "getTipos: $tipo")
                    val id = tipo.url.split("/").let { it[it.size - 2] }
                    val imagem = PokemonUtils.getPokemonTypeImageUrl(id.toIntOrNull() ?: -1)
                    tipos.add(TipoDTO(id.toIntOrNull() ?: -1, tipo.nome, imagem))
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "getTipos: Error: ${e.message}")
            e.printStackTrace()
        }

        return tipos

    }


    suspend fun getByTipo(id: String): ArrayList<PokemonDTO> {
        try {

            val response = pokemonApi.getTypeById(id)

            val pokemons = ArrayList<PokemonDTO>()

            response?.let { tipo ->

                tipo.pokemons.forEach { pokemonTipoResponse ->

                    val pokemon =
                        pokemonApi.getPokemonById(pokemonTipoResponse.pokemon.url.split("/")
                            .let { it[it.size - 2] })

                    if (pokemon != null) {

                        val tipos = pokemon.tipos.map { type ->

                            val idTipo = type.tipo.url.split("/").let { it[it.size - 2] }
                            val imagem =
                                PokemonUtils.getPokemonTypeImageUrl(idTipo.toIntOrNull() ?: -1)
                            TipoDTO(
                                idTipo.toIntOrNull() ?: -1, type.tipo.nome, imagem
                            )
                        }

                        pokemons.add(
                            PokemonDTO(
                                pokemon.id,
                                pokemon.nome,
                                PokemonUtils.getPokemonImageUrl(pokemon.id),
                                tipos
                            )
                        )
                    }
                }
            }

            return pokemons

        } catch (e: Exception) {
            Log.e(TAG, "getOne: Error: ${e.message}")
            e.printStackTrace()
            return arrayListOf()

        }

    }

    companion object {
        private const val TAG = "PokemonRepository"

    }

}