package com.sntsb.mypokedex.utils

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
    }
}