package com.example.uas_travelapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_travelapp.Fragment.HomeAdminFragmentDirections
import com.example.uas_travelapp.Model.Travel
import com.example.uas_travelapp.databinding.TravelEditCardBinding

class EditTravelAdapter(private val listTravel: List<Travel>)
    : RecyclerView.Adapter<EditTravelAdapter.ItemEditTravelViewHolder>() {
    inner  class ItemEditTravelViewHolder(private val binding: TravelEditCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travel: Travel) {
            with(binding) {
                txtTitle.text = travel.title
                txtAsal.text = ": ${travel.asal}"
                txtTujuan.text = ": ${travel.tujuan}"
                txtHarga.text = ": Rp${travel.hargaEkonomi},00"
                btnEdit.setOnClickListener {
                    val action = HomeAdminFragmentDirections.actionHomeAdminFragment2ToManageTravelActivity(travel.id)
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemEditTravelViewHolder {
        val binding = TravelEditCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemEditTravelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTravel.size
    }

    override fun onBindViewHolder(holder: ItemEditTravelViewHolder, position: Int) {
        holder.bind(listTravel[position])
    }
}