package com.sntsb.mypokedex.utils

import com.google.gson.GsonBuilder

class StringUtils {
    companion object {

        fun primeiraLetraCapitalize(string: String): String {

            if (string.isEmpty()) {
                return string
            }
            val firstChar = string[0].uppercaseChar()
            val restOfString = string.substring(1)
            return "$firstChar$restOfString"
        }

        fun todasMinusculas(string: String): String {
            return string.lowercase()
        }

        fun printJSON(map: Map<String, Any?>): String {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(map)

            return jsonString.replace("\n", "\n\t")
        }
    }
}