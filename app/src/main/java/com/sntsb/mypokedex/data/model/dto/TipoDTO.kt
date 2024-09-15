package com.sntsb.mypokedex.data.model.dto

data class TipoDTO(

    val id: Int, val descricao: String, val imagem: String

) {
    override fun toString(): String {
        return "TipoDTO(id=$id, descricao='$descricao', imagem='$imagem')"
    }

}