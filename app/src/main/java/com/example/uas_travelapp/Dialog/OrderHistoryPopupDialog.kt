package com.example.uas_travelapp.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.uas_travelapp.Fragment.OrderHistoryFragment
import com.example.uas_travelapp.Model.Order

class OrderHistoryPopupDialog (private val order: Order,
                               private val historyFragment: OrderHistoryFragment
)
    : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setTitle("Batalkan perjalanan?")
            setMessage("Batalkan perjalanan yang kamu pesan ini?")
            setPositiveButton("Hapus") { dialog, which->
                historyFragment.deleteOrder(order)
                dismiss()
            }
        }
        return builder.create()
    }

}