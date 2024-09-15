package com.sntsb.mypokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sntsb.mypokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    var pokemonPager = repository.getPokemonPager().flow.cachedIn(viewModelScope)

    fun refreshPokemonList() {
        pokemonPager = repository.restart().flow.cachedIn(viewModelScope)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}