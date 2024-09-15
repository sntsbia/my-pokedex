package com.sntsb.mypokedex.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sntsb.mypokedex.databinding.FragmentPokemonListBinding
import com.sntsb.mypokedex.ui.adapter.ItemPokemonAdapter
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

        pokemonAdapter = ItemPokemonAdapter(requireContext())

        mPokemonListViewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        binding.rvPokemons.adapter = pokemonAdapter
        binding.rvPokemons.layoutManager = LinearLayoutManager(requireContext())

        binding.progressbar.visibility = View.GONE

        binding.cgFiltro.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.e(TAG, "onCreate: $checkedIds")

        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressbar.visibility = View.VISIBLE

            mPokemonListViewModel.refreshPokemonList()

            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressbar.visibility = View.GONE
        }

        lifecycleScope.launch {
            mPokemonListViewModel.pokemonPager.collectLatest { pagingData ->
                Log.e(TAG, "onViewCreated: ################# ${pagingData}")
                binding.progressbar.visibility = View.VISIBLE

                pokemonAdapter.submitData(pagingData)

                binding.progressbar.visibility = View.GONE
            }
        }

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