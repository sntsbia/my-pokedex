package com.sntsb.mypokedex.ui.adapter

import android.content.Context
import android.net.http.UrlRequest.Status
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sntsb.mypokedex.data.model.dto.StatusDTO
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.databinding.ItemStatusBinding
import com.sntsb.mypokedex.databinding.ItemTipoBinding

class StatusAdapter(private val status: List<StatusDTO>, val context: Context) :
    RecyclerView.Adapter<StatusAdapter.StatusItemViewHolder>() {

    inner class StatusItemViewHolder(private val databinding: ItemStatusBinding) :
        RecyclerView.ViewHolder(databinding.root) {

        fun bind(tipo: StatusDTO) {
            databinding.statusItemDataBinding = tipo
            databinding.tvStatus.text = tipo.descricao
            databinding.tvEstatistica.text = buildString { append(tipo.valor) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusItemViewHolder {
        val binding = ItemStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatusItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return status.size
    }

    override fun onBindViewHolder(holder: StatusItemViewHolder, position: Int) {
        val statusDTO = status[position]
        holder.bind(statusDTO)
    }


}