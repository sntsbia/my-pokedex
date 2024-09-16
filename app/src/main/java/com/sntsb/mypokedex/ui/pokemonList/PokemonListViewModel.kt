package com.sntsb.mypokedex.ui.pokemonList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.data.repository.PokemonRepository
import com.sntsb.mypokedex.data.repository.TipoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val tipoRepository: TipoRepository
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _queryString = MutableLiveData<String>()

    private val _pokemonsByTipo = MutableLiveData<ArrayList<PokemonDTO>>()

    val pagingData: LiveData<PagingData<PokemonDTO>> = _queryString.switchMap { query ->
        repository.getPager(query).cachedIn(viewModelScope).asLiveData()
    }

    val pagingDataByTipo: LiveData<PagingData<PokemonDTO>> = _pokemonsByTipo.switchMap { pokemons ->

        repository.getPokemonByTipoPager(pokemons).flow.cachedIn(viewModelScope).asLiveData()
    }

    val tipos: LiveData<ArrayList<TipoDTO>> = liveData {
        val tipos = tipoRepository.getTipos()
        emit(tipos)
    }

    fun setByTipo(tipo: String) {

        _loading.value = true
        viewModelScope.launch {

            if (tipo.isEmpty()) {
                setQueryString("")

            } else {

                val pokemons = tipoRepository.getByTipo(tipo)
                _pokemonsByTipo.value = pokemons
            }


            _loading.value = false

        }

    }

    fun setQueryString(queryString: String) {
        _queryString.value = queryString
    }

    fun setLoading(loading: Boolean) {
        _loading.value = loading
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}