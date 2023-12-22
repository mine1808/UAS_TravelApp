package com.example.uas_travelapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_travelapp.Model.PendingAction
import com.example.uas_travelapp.databinding.PendingActionCardBinding

class PendingActionAdapter(private val listAction: List<PendingAction>)
    : RecyclerView.Adapter<PendingActionAdapter.ItemPendingActionViewHolder>() {
    inner class ItemPendingActionViewHolder(private val binding: PendingActionCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(action: PendingAction) {
            with(binding) {
                txtTitle.text = action.title
                txtDesc.text = "${action.asal} - ${action.tujuan}, Rp${action.hargaEkonomi} - Rp${action.hargaEksekutif}"
                txtType.text = action.type.uppercase()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPendingActionViewHolder {
        val binding = PendingActionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPendingActionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAction.size
    }

    override fun onBindViewHolder(holder: ItemPendingActionViewHolder, position: Int) {
        holder.bind(listAction[position])
    }
}