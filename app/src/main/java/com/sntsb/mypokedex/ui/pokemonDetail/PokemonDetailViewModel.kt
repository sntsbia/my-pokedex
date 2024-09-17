package com.sntsb.mypokedex.ui.pokemonDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sntsb.mypokedex.data.model.PokemonModel
import com.sntsb.mypokedex.data.model.dto.PokemonDetalhesDTO
import com.sntsb.mypokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemon = MutableLiveData<PokemonDetalhesDTO?>()
    val pokemon: LiveData<PokemonDetalhesDTO?> get() = _pokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> get() = _id

    private val _fotoVisivelIndex = MutableLiveData<Int>()
    val fotoVisivelIndex: LiveData<Int> get() = _fotoVisivelIndex

    fun setFotoVisivelIndex(index: Int) {
        _fotoVisivelIndex.value = index
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun getPokemon(id: String) {
        viewModelScope.launch {
            Log.e(TAG, "getPokemon: $id")

            _isLoading.value =(true)

            val pokemon = repository.getOne(id)
            Log.e(TAG, "getPokemon: $pokemon")
            _pokemon.value = pokemon

            _isLoading.value =(false)

        }
    }

    companion object {
        private const val TAG = "PokemonDetailViewModel"
    }


}