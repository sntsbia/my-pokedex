package com.sntsb.mypokedex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.databinding.ItemTipoBinding

class TipoAdapter(private val tipos: List<TipoDTO>, val context: Context) :
    RecyclerView.Adapter<TipoAdapter.TipoItemViewHolder>() {

    inner class TipoItemViewHolder(private val databinding: ItemTipoBinding) :
        RecyclerView.ViewHolder(databinding.root) {

        fun bind(tipo: TipoDTO) {
            databinding.tipoItemDataBinding = tipo

            Glide.with(databinding.root).load(tipo.imagem).into(databinding.ivTipo)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoItemViewHolder {
        val binding = ItemTipoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TipoItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tipos.size
    }

    override fun onBindViewHolder(holder: TipoItemViewHolder, position: Int) {
        val tipoDTO = tipos[position]
        holder.bind(tipoDTO)
    }


}