package com.sntsb.mypokedex.data.model.dto

data class StatusDTO (
    val descricao: String,
    val valor: Int
) {
    override fun toString(): String {
        return buildString {
            append("StatusDTO(descricao='$descricao', valor=$valor)")
        }

    }
}