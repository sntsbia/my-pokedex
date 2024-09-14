package com.sntsb.mypokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sntsb.mypokedex.data.repository.PokemonRepository
import javax.inject.Inject

//@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    private val _texto = MutableLiveData("Olá, mundo!")

    val texto: LiveData<String> = _texto

    fun setTexto() {
        _texto.value = repository.getPokemonList()
    }

}