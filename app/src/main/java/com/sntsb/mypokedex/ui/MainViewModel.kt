package com.sntsb.mypokedex.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sntsb.mypokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    private val _texto = MutableLiveData<String>()
    val texto: LiveData<String> = _texto

    fun setTexto(novoTexto: String = repository.getPokemonList()) {

        _texto.value = novoTexto
        Log.e(TAG, "setTexto: ${texto.value}", )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}