package com.sntsb.mypokedex.data.repository

import com.sntsb.mypokedex.data.PokemonApi

/** Classe que define o repositório de dados do aplicativo (faz chamadas para API) **/
//class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) {
class PokemonRepository constructor() {

    fun getPokemonList() = "Chamou PokemonRepository"

}