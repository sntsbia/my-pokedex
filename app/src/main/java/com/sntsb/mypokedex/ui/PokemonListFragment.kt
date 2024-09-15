package com.sntsb.mypokedex.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.sntsb.mypokedex.databinding.FragmentPokemonListBinding
import com.sntsb.mypokedex.ui.adapter.ItemPokemonAdapter
import com.sntsb.mypokedex.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!

    private lateinit var mPokemonListViewModel: PokemonListViewModel

    private lateinit var pokemonAdapter: ItemPokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPokemonListViewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        pokemonAdapter = ItemPokemonAdapter(requireContext())
        binding.rvPokemons.adapter = pokemonAdapter
        binding.rvPokemons.layoutManager = GridLayoutManager(requireContext(),2)

        binding.swipeRefreshLayout.setOnRefreshListener {

            mPokemonListViewModel.refreshPokemonList()
            binding.rvPokemons.layoutManager?.scrollToPosition(0)

        }

        lifecycleScope.launch {

            mPokemonListViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
                mPokemonListViewModel.getPokemonPager(query)
            }

            mPokemonListViewModel.loading.observe(viewLifecycleOwner) { loading ->
                setLoading(loading)
            }

            mPokemonListViewModel.pokemonPager.collectLatest { pagingData ->
                Log.e(TAG, "onViewCreated: atualizou")

                pokemonAdapter.submitData(pagingData)

            }
        }

        lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collectLatest { loadStates ->
                mPokemonListViewModel.setLoading(loadStates.refresh is LoadState.Loading)
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            when (id){
                binding.radioPokemon.id -> {
                    binding.llSearch.visibility = View.VISIBLE
                    binding.tilDdSearch.visibility = View.GONE
                }
                binding.radioTipo.id -> {
                    binding.llSearch.visibility = View.GONE
                    binding.tilDdSearch.visibility = View.VISIBLE
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.txtSearch.text.toString()
            if(mPokemonListViewModel.searchQuery.value != query) {

                mPokemonListViewModel.setSearchQuery(StringUtils.todasMinusculas(query))
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = loading
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

        private const val TAG = "PokemonListFragment"

        @JvmStatic
        fun newInstance() = PokemonListFragment()
    }
}