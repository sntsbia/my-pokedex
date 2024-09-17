package com.sntsb.mypokedex.ui.pokemonList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.intelipec.firebase.dropdown.DropdownTipoList
import com.sntsb.mypokedex.R
import com.sntsb.mypokedex.data.model.enums.TiposEnum
import com.sntsb.mypokedex.databinding.FragmentPokemonListBinding
import com.sntsb.mypokedex.ui.adapter.ItemPokemonAdapter
import com.sntsb.mypokedex.ui.adapter.LoadStateAdapter
import com.sntsb.mypokedex.utils.StringUtils
import com.sntsb.mypokedex.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding get() = _binding!!

    private lateinit var mPokemonListViewModel: PokemonListViewModel

    private lateinit var pokemonAdapter: ItemPokemonAdapter
    private lateinit var tiposAdapter: DropdownTipoList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPokemonListViewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]

        pokemonAdapter = ItemPokemonAdapter(requireContext())
        binding.rvPokemons.adapter =
            pokemonAdapter.withLoadStateFooter(footer = LoadStateAdapter(object :
                LoadStateAdapter.OnAction {
                override fun isLoading(loading: Boolean) {
                    setLoading(loading)
                }
            }))
        binding.rvPokemons.layoutManager = GridLayoutManager(requireContext(), resources.getInteger(
            R.integer.colunas_grid))

        binding.swipeRefreshLayout.setOnRefreshListener {

            mPokemonListViewModel.setQueryString("")
            binding.rvPokemons.layoutManager?.scrollToPosition(0)

        }

        mPokemonListViewModel.setQueryString("")

        mPokemonListViewModel.loading.observe(viewLifecycleOwner) { loading ->
            setLoading(loading)
        }

        mPokemonListViewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            if (binding.radioGroup.checkedRadioButtonId == binding.radioPokemon.id || binding.ddSearch.text.isNullOrEmpty()) {
                pokemonAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
        mPokemonListViewModel.pagingDataByTipo.observe(viewLifecycleOwner) { pagingData ->
            if (binding.radioGroup.checkedRadioButtonId == binding.radioTipo.id && !binding.ddSearch.text.isNullOrEmpty()) {
                pokemonAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }

        mPokemonListViewModel.tipos.observe(viewLifecycleOwner) { tipos ->
            tiposAdapter = DropdownTipoList(
                requireContext(), tipos, android.R.layout.simple_spinner_dropdown_item
            )
            binding.ddSearch.setAdapter(tiposAdapter)

            binding.ddSearch.setOnItemClickListener { parent, view, position, id ->
                val tipo = tiposAdapter.getItem(position)
                mPokemonListViewModel.setByTipo(tipo.id.toString())
                binding.btnLimpar.visibility = View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collectLatest { loadStates ->
                mPokemonListViewModel.setLoading(loadStates.refresh is LoadState.Loading)
            }
        }

        pokemonAdapter.addLoadStateListener { loadStates ->
            val isEmpty =
                pokemonAdapter.itemCount == 0 && loadStates.refresh is LoadState.NotLoading
            if (isEmpty) {
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.rvPokemons.visibility = View.GONE
            } else {
                binding.tvEmptyList.visibility = View.GONE
                binding.rvPokemons.visibility = View.VISIBLE
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                binding.radioPokemon.id -> {
                    binding.llSearch.visibility = View.VISIBLE
                    binding.llDdSearch.visibility = View.GONE
                }

                binding.radioTipo.id -> {
                    binding.llSearch.visibility = View.GONE
                    binding.llDdSearch.visibility = View.VISIBLE
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.txtSearch.text.toString()
            if (mPokemonListViewModel.searchQuery.value != query) {

                mPokemonListViewModel.setQueryString(StringUtils.todasMinusculas(query))
            }
        }

        binding.btnLimpar.setOnClickListener {
            binding.ddSearch.setText("")
            binding.btnLimpar.visibility = View.GONE
            mPokemonListViewModel.setByTipo("")
        }

        binding.tilSearch.setEndIconOnClickListener {
            binding.txtSearch.setText("")
            mPokemonListViewModel.setQueryString("")
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