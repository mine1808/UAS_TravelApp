package com.example.uas_travelapp.Dialog

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.uas_travelapp.Fragment.ManageUserFragment
import com.example.uas_travelapp.Model.User
import com.example.uas_travelapp.databinding.ManageUserDialogBinding

class ManageUserPopupDialog(private val user: User, private val manageUserFragment: ManageUserFragment): DialogFragment() {
    private val binding by lazy {
        ManageUserDialogBinding.inflate(layoutInflater)
    }
    private val listRole = listOf<String>("Admin", "User")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setView(binding.root)
            setTitle("Kelola Pengguna")
            with(binding) {
                txtNama.text = user.nama
                txtUsername.text = user.username
                txtEmail.text = user.email
                spinnerRole.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, listRole)
                if (user.role == "admin") {
                    spinnerRole.setSelection(0)
                }
                else if (user.role == "user") {
                    spinnerRole.setSelection(1)
                }
            }
            setPositiveButton("Simpan") { dialog, which ->
                user.role = listRole[binding.spinnerRole.selectedItemPosition].lowercase()
                manageUserFragment.setUserChange(user)
            }
            setNeutralButton("Hapus Akun") { dialog, which ->
                manageUserFragment.deleteUser(user)
            }
        }
        return builder.create()
    }
}