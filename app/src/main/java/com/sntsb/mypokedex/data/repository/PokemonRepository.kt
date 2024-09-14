package com.sntsb.mypokedex.data.repository

import com.sntsb.mypokedex.data.PokemonApi
import javax.inject.Inject

/** Classe que define o repositório de dados do aplicativo (faz chamadas para API) **/
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) {

    fun getPokemonList() = "Chamou PokemonRepository"

}