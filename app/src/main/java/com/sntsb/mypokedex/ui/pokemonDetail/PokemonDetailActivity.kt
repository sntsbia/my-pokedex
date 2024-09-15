package com.sntsb.mypokedex.ui.pokemonDetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sntsb.mypokedex.R
import com.sntsb.mypokedex.data.model.dto.PokemonDetalhesDTO
import com.sntsb.mypokedex.databinding.ActivityPokemonDetailBinding
import com.sntsb.mypokedex.ui.adapter.StatusAdapter
import com.sntsb.mypokedex.ui.adapter.TipoAdapter
import com.sntsb.mypokedex.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailActivity : AppCompatActivity() {

    companion object {
        const val POKEMON_ID = "pokemon_id"

        private const val TAG = "PokemonDetailActivity"
    }

    private lateinit var binding : ActivityPokemonDetailBinding

    private val viewModel: PokemonDetailViewModel by viewModels()

    private var tipoAdapter: TipoAdapter? = null
    private var statusAdapter: StatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_detail)
        supportActionBar?.hide()

        binding.toolbar.setNavigationOnClickListener {

            finish()

        }

        enableEdgeToEdge()

        lifecycleScope.launch {

            viewModel.fotoVisivelIndex.observe(this@PokemonDetailActivity) { index ->
                if(index != null) {
                    viewModel.pokemon.value?.imagem?.get(index)?.let {
                        Glide.with(binding.root).load(it.url).into(binding.ivPokemon)
                    }
                }
            }

            viewModel.pokemon.observe(this@PokemonDetailActivity) { pokemon ->
                Log.e(TAG, "onCreate: $pokemon", )
                if(pokemon != null) {

                    initDados(pokemon)
                }
            }

            viewModel.isLoading.observe(this@PokemonDetailActivity) { isLoading ->
                isLoading(isLoading)
            }

            viewModel.id.observe(this@PokemonDetailActivity) { id ->
                if(id != null) {
                    viewModel.getPokemon(id)
                }
            }

        }

        val pokemonId = intent.getIntExtra(POKEMON_ID, -1)

        viewModel.setId(pokemonId.toString())

    }

    private fun isLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressbarDetails.visibility = View.VISIBLE
        } else {
            binding.progressbarDetails.visibility = View.GONE
        }
    }

    private fun initDados(pokemon: PokemonDetalhesDTO) {
        with(binding) {
            Log.e(TAG, "initDados: $pokemon", )
            tvDescricao.text = StringUtils.primeiraLetraCapitalize(pokemon.nome)

            tipoAdapter = TipoAdapter(pokemon.tipos, this@PokemonDetailActivity)
            rvTipos.adapter = tipoAdapter
            rvTipos.layoutManager = GridLayoutManager(this@PokemonDetailActivity, 3)

            statusAdapter = StatusAdapter(pokemon.status, this@PokemonDetailActivity)
            rvDetalhes.adapter = statusAdapter
            rvDetalhes.layoutManager = LinearLayoutManager(this@PokemonDetailActivity)

            viewModel.setFotoVisivelIndex(0)

            ibProximo.setOnClickListener {
                viewModel.fotoVisivelIndex.value?.let { index ->
                    if(index < pokemon.imagem.size - 1) {
                        viewModel.setFotoVisivelIndex(index + 1)
                    } else {
                        viewModel.setFotoVisivelIndex(0)
                    }
                }
            }

            ibVoltar.setOnClickListener {
                viewModel.fotoVisivelIndex.value?.let { index ->
                    if (index > 0) {
                        viewModel.setFotoVisivelIndex(index - 1)
                    } else {
                        viewModel.setFotoVisivelIndex(pokemon.imagem.size - 1)
                    }
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}