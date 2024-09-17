package com.sntsb.mypokedex.utils

import android.content.Context
import android.util.Log
import com.sntsb.mypokedex.R

class UiUtils (val context: Context) {

    companion object {
        private const val TAG = "UiUtils"
    }

    fun getStatusLabel(status: String): String {
        Log.e(TAG, "getStatusLabel: $status")
        return when(status) {
            "hp" -> context.resources.getString(R.string.hp)
            "attack" -> context.resources.getString(R.string.attack)
            "defense" -> context.resources.getString(R.string.defense)
            "special-attack" -> context.resources.getString(R.string.special_attack)
            "special-defense" -> context.resources.getString(R.string.special_defense)
            "speed" -> context.resources.getString(R.string.speed)
            "total" -> context.resources.getString(R.string.total)
            else -> status
        }

    }

    fun getTipoByLabel(label: String): String {
        when (label) {
             context.resources.getString(R.string.normal) -> return "normal"
             context.resources.getString(R.string.fighting) -> return "fighting"
             context.resources.getString(R.string.flying) -> return "flying"
             context.resources.getString(R.string.poison) -> return "poison"
             context.resources.getString(R.string.ground) -> return "ground"
             context.resources.getString(R.string.rock) -> return "rock"
             context.resources.getString(R.string.bug) -> return "bug"
             context.resources.getString(R.string.ghost) -> return "ghost"
             context.resources.getString(R.string.steel) -> return "steel"
             context.resources.getString(R.string.fire) -> return "fire"
             context.resources.getString(R.string.water) -> return "water"
             context.resources.getString(R.string.grass) -> return "grass"
             context.resources.getString(R.string.electric) -> return "electric"
             context.resources.getString(R.string.psychic) -> return "psychic"
             context.resources.getString(R.string.ice) -> return "ice"
             context.resources.getString(R.string.dragon) -> return "dragon"
             context.resources.getString(R.string.dark) -> return "dark"
             context.resources.getString(R.string.fairy) -> return "fairy"
             context.resources.getString(R.string.shadow) -> return "shadow"
            else -> return label
        }
    }

    fun getTipoLabel(tipo: String): String {
        when (tipo) {
            "normal" -> return context.resources.getString(R.string.normal)
            "fighting" -> return context.resources.getString(R.string.fighting)
            "flying" -> return context.resources.getString(R.string.flying)
            "poison" -> return context.resources.getString(R.string.poison)
            "ground" -> return context.resources.getString(R.string.ground)
            "rock" -> return context.resources.getString(R.string.rock)
            "bug" -> return context.resources.getString(R.string.bug)
            "ghost" -> return context.resources.getString(R.string.ghost)
            "steel" -> return context.resources.getString(R.string.steel)
            "fire" -> return context.resources.getString(R.string.fire)
            "water" -> return context.resources.getString(R.string.water)
            "grass" -> return context.resources.getString(R.string.grass)
            "electric" -> return context.resources.getString(R.string.electric)
            "psychic" -> return context.resources.getString(R.string.psychic)
            "ice" -> return context.resources.getString(R.string.ice)
            "dragon" -> return context.resources.getString(R.string.dragon)
            "dark" -> return context.resources.getString(R.string.dark)
            "fairy" -> return context.resources.getString(R.string.fairy)
            "shadow" -> return context.resources.getString(R.string.shadow)
            else -> return tipo

        }
    }

    fun getCorTipo(tipo: String): Int {
        when (tipo) {

            "normal" -> return R.color.color_pokemon_normal
            "fighting" -> return R.color.color_pokemon_lutador
            "flying" -> return R.color.color_pokemon_voador
            "poison" -> return R.color.color_pokemon_veneno
            "ground" -> return R.color.color_pokemon_terra
            "rock" -> return R.color.color_pokemon_pedra
            "bug" -> return R.color.color_pokemon_inseto
            "ghost" -> return R.color.color_pokemon_fantasma
            "steel" -> return R.color.color_pokemon_metalico
            "fire" -> return R.color.color_pokemon_fogo
            "water" -> return R.color.color_pokemon_agua
            "grass" -> return R.color.color_pokemon_grama
            "electric" -> return R.color.color_pokemon_eletrico
            "psychic" -> return R.color.color_pokemon_psiquico
            "ice" -> return R.color.color_pokemon_gelo
            "dragon" -> return R.color.color_pokemon_dragao
            "dark" -> return R.color.color_pokemon_sombrio
            "fairy" -> return R.color.color_pokemon_fada
        }

        return R.color.color_secundaria_roxo
    }



}