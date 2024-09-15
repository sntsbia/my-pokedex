package com.sntsb.mypokedex.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sntsb.mypokedex.databinding.ItemPokemonBinding
import com.sntsb.mypokedex.model.dto.PokemonDTO

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
            databinding.tvNomePokemon.text = pokemon.nome

            Glide.with(databinding.root).load(pokemon.imagem).into(databinding.ivPokemon)

            databinding.cvItemPokemon.setOnClickListener {

                Snackbar.make(
                    databinding.root,
                    "${pokemon.let { "${it.nome} (${it.id})" }}}",
                    Snackbar.LENGTH_LONG).show()
                Log.e(TAG, "bind: Clicked: $pokemon")
            }

        }

    }


    companion object {
        const val TAG = "ItemPokemonAdapter"

        private val POKEMON_COMPARATOR = object : DiffUtil.ItemCallback<PokemonDTO>() {
            // ...
            override fun areItemsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PokemonDTO, newItem: PokemonDTO): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}