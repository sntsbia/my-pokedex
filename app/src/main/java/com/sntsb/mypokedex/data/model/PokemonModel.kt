package com.sntsb.mypokedex.data.model

data class PokemonModel(
    val id: Int, val nome: String, val status: List<PokemonStatusModel>, val tipos: List<TipoModel>
)
