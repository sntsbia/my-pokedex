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
import com.sntsb.mypokedex.data.model.enums.TiposEnum
import com.sntsb.mypokedex.databinding.FragmentPokemonListBinding
import com.sntsb.mypokedex.ui.adapter.ItemPokemonAdapter
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
        binding.rvPokemons.adapter = pokemonAdapter
        binding.rvPokemons.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.swipeRefreshLayout.setOnRefreshListener {

            mPokemonListViewModel.setQueryString("")
            binding.rvPokemons.layoutManager?.scrollToPosition(0)

        }
        initDropdown()


        mPokemonListViewModel.setQueryString("")

        mPokemonListViewModel.loading.observe(viewLifecycleOwner) { loading ->
            setLoading(loading)
        }

        mPokemonListViewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            pokemonAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collectLatest { loadStates ->
                mPokemonListViewModel.setLoading(loadStates.refresh is LoadState.Loading)
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
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
            if (mPokemonListViewModel.searchQuery.value != query) {

                mPokemonListViewModel.setQueryString(StringUtils.todasMinusculas(query))
            }
        }

        binding.tilSearch.setEndIconOnClickListener {
            binding.txtSearch.setText("")
            mPokemonListViewModel.setQueryString("")
        }
    }

    private fun initDropdown() {
        val tipos = TiposEnum.entries.map {
            UiUtils(requireContext()).getTipoLabel(it.stringResourceName)
        }.toList().let {
            ArrayList(it)
        }

        tipos.sort()

        tiposAdapter = DropdownTipoList(
            requireContext(),
            tipos,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item
        )
        binding.ddSearch.setAdapter(tiposAdapter)
        tiposAdapter.notifyDataSetChanged()

        binding.ddSearch.setOnItemClickListener { parent, view, position, id ->
            val tipo = tiposAdapter.getItem(position)

            Log.e(TAG, "initDropdown: $tipo")


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