package com.sntsb.mypokedex.utils

import com.sntsb.mypokedex.data.paging.PokemonPagingSource
import com.sntsb.mypokedex.data.paging.PokemonPagingSource.Companion

class PokemonUtils {
    companion object {
        private const val URL_IMAGEM_DEFAULT =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%d.png"

        private const val URL_IMAGEM_SHINY =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/%d.png"

        private const val URL_IMAGEM_TIPO =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/%d.png"

        fun getPokemonImageUrl(id: Int): String {
            return if(id >= 0) {
                String.format(java.util.Locale.getDefault(),
                    URL_IMAGEM_DEFAULT, id)
            } else {
                ""
            }
        }

        fun getPokemonShinyImageUrl(id: Int): String {
            return if(id >= 0) {
                return String.format(
                    java.util.Locale.getDefault(),
                    URL_IMAGEM_SHINY, id
                )
            } else {
                ""
            }
        }

        fun getPokemonTypeImageUrl(id: Int): String {
            return if(id >= 0) {
                String.format(java.util.Locale.getDefault(),
                    URL_IMAGEM_TIPO, id)
            } else {
                ""
            }
        }

    }

}