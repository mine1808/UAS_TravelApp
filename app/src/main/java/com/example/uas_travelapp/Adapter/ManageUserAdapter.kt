package com.example.uas_travelapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_travelapp.Dialog.ManageUserPopupDialog
import com.example.uas_travelapp.Model.User
import com.example.uas_travelapp.databinding.ManageUserCardBinding

class ManageUserAdapter(private val listUser: List<User>)
    : RecyclerView.Adapter<ManageUserAdapter.ItemManageUserViewHolder>() {
    inner class ItemManageUserViewHolder(private val binding: ManageUserCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                txtNama.text = user.nama
                txtRole.text = user.role.replaceFirstChar { it.uppercase() }
                btnManage.setOnClickListener {
                    val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                    val dialog = ManageUserPopupDialog(user, itemView.findFragment())
                    dialog.show(fragmentManager, "Manage User Dialog")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemManageUserViewHolder {
        val binding = ManageUserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemManageUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ItemManageUserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}