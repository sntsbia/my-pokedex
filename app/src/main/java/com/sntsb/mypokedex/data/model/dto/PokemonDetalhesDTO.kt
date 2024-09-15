package com.sntsb.mypokedex.data.model.dto

data class PokemonDetalhesDTO (
    val id: Int,
    val nome: String,
    val imagem: List<ImagemDTO>,
    val altura: Int,
    val peso: Int,
    val tipos: List<TipoDTO>,
    val status: List<StatusDTO>
) {
    override fun toString(): String {
        return buildString {
            append("PokemonDetalhesDTO(id=$id, nome='$nome', imagem=$imagem, altura=$altura, peso=$peso, tipos=$tipos, status=$status)")
        }
    }
}