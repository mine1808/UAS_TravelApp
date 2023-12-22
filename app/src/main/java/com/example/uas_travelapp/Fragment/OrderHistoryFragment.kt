package com.example.uas_travelapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_travelapp.Activity.DashboardUserActivity
import com.example.uas_travelapp.Adapter.OrderedTravelAdapter
import com.example.uas_travelapp.Dialog.OrderHistoryPopupDialog
import com.example.uas_travelapp.Model.Order
import com.example.uas_travelapp.R
import com.example.uas_travelapp.databinding.FragmentOrderHistoryBinding
import com.google.firebase.firestore.FirebaseFirestore

class OrderHistoryFragment : Fragment() {
    private val binding by lazy {
        FragmentOrderHistoryBinding.inflate(layoutInflater)
    }
    private val firestore = FirebaseFirestore.getInstance()
    private val ordersCollectionRef = firestore.collection("orders")
    val ordersLiveData: MutableLiveData<List<Order>> by lazy {
        MutableLiveData<List<Order>>()
    }
    private val loginEmail = DashboardUserActivity.loginEmail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersLiveData.observe(viewLifecycleOwner) { orders->
            binding.rvContent.apply {
                adapter = OrderedTravelAdapter(orders) { selectedOrder ->
                    showPopupDialog(selectedOrder)
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            if (orders.isEmpty()) {
                binding.txtMsg.visibility = View.VISIBLE
            }
            else {
                binding.txtMsg.visibility = View.GONE
            }
        }
        observeOrderChanges()
        binding.btnAddPlan.setOnClickListener {
            val action = OrderHistoryFragmentDirections.actionOrderHistoryFragment2ToHomeUserFragment2()
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun showPopupDialog(selectedOrder: Order) {
        val popupDialog = OrderHistoryPopupDialog(selectedOrder, this)
        popupDialog.show(childFragmentManager, "Popup Dialog")
    }

    public fun deleteOrder(order: Order) {
        if (order.id.isEmpty()) {
            Log.d("User Order", "Error no order id")
        }
        ordersCollectionRef.document(order.id).delete().addOnFailureListener {
            Log.d("User Order", "Error deleting order: $it")
        }
    }

    private fun observeOrderChanges() {
        ordersCollectionRef.whereEqualTo("user_email", loginEmail).addSnapshotListener { value, error ->
            if (error != null) {
                Log.d("User HistoryFragment", "Error listening: $error")
            }
            val orders = value?.toObjects(Order::class.java)
            if (orders != null) {
                ordersLiveData.postValue(orders)
            }
        }
    }
}