package com.sntsb.mypokedex.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sntsb.mypokedex.databinding.ItemPokemonBinding
import com.sntsb.mypokedex.data.model.dto.PokemonDTO
import com.sntsb.mypokedex.ui.pokemonDetail.PokemonDetailActivity
import com.sntsb.mypokedex.utils.StringUtils
import com.sntsb.mypokedex.utils.UiUtils

class ItemPokemonAdapter(private val mContext: Context) :
    PagingDataAdapter<PokemonDTO, ItemPokemonAdapter.PokemonViewHolder>(POKEMON_COMPARATOR) {
    private val context = mContext

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        if (pokemon != null) {
            holder.bind(pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return PokemonViewHolder(binding)

    }

    inner class PokemonViewHolder(private val databinding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(databinding.root) {

        fun bind(pokemon: PokemonDTO) {
            databinding.pokemonItemDataBinding = pokemon
            databinding.tvNomePokemon.text = StringUtils.primeiraLetraCapitalize(pokemon.nome)

            Glide.with(databinding.root).load(pokemon.imagem).into(databinding.ivPokemon)

            if(pokemon.tipos.isNotEmpty()){
                pokemon.tipos.firstOrNull()?.let { tipo ->
                    databinding.cvItemPokemon.backgroundTintList = ContextCompat.getColorStateList(context, UiUtils(context).getCorTipo(tipo.descricao))
                }
            }

            databinding.cvItemPokemon.setOnClickListener {

                mContext.startActivity(
                    Intent(context, PokemonDetailActivity::class.java).apply {
                        putExtra(PokemonDetailActivity.POKEMON_ID, pokemon.id)
                    }
                )

            }

        }

    }


    companion object {
        const val TAG = "ItemPokemonAdapter"

        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<PokemonDTO>() {
            // ...
            override fun areItemsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}