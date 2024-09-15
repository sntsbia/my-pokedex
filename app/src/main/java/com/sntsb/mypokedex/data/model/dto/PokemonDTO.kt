package com.sntsb.mypokedex.data.model.dto

data class PokemonDTO(
    val id: Int, val nome: String, val imagem: String
){
    override fun toString(): String {
        return "PokemonDTO(id=$id, nome='$nome', imagem='$imagem')"
    }
}