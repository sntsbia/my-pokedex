package com.sntsb.mypokedex.data.model.dto

data class ImagemDTO (
    val nome: String,
    val url: String
) {
    override fun toString(): String {
        return buildString {
            append("ImagemDTO(nome='$nome', url='$url')")
        }

    }
    companion object {
        const val IMAGEM_FRONT = "front"
        const val IMAGEM_SHINY = "shiny"

    }
}