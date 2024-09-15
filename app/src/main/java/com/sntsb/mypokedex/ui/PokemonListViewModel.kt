package com.sntsb.mypokedex.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
        private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.value = query

    }
    var pokemonPager = repository.pagingSource("").flow.cachedIn(viewModelScope)

    fun getPokemonPager(query: String) {
        _loading.value = true
        pokemonPager = repository.pagingSource(query).flow.cachedIn(viewModelScope)
        _loading.value = false
    }

    fun refreshPokemonList() {
        _loading.value = true
        pokemonPager = repository.pagingSource("").flow.cachedIn(viewModelScope)

        _loading.value = false

    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}