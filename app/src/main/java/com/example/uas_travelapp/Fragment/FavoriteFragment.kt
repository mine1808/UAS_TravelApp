package com.example.uas_travelapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_travelapp.Activity.DashboardUserActivity
import com.example.uas_travelapp.Adapter.TravelAdapter
import com.example.uas_travelapp.Model.Travel
import com.example.uas_travelapp.R
import com.example.uas_travelapp.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment()  {
    private val binding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }
    private val travelsLiveData: MutableLiveData<List<Travel>> by lazy {
        MutableLiveData<List<Travel>>()
    }
    private val loginEmail = DashboardUserActivity.loginEmail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAllFavorites()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getAllFavorites()
    }

    private fun navigateToOrderActivity(travel: Travel) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToOrderActivity(travel.id)
        findNavController().navigate(action)
    }

    private fun getAllFavorites() {
        val listTravel = mutableListOf<Travel>()
        DashboardUserActivity.favorites.getUserFavorites(loginEmail).observe(viewLifecycleOwner) { favs ->
            listTravel.clear()
            favs.forEach { fav ->
                val travel = Travel(fav.id_travel, fav.title, fav.asal, fav.tujuan, fav.hargaEkonomi)
                listTravel.add(travel)
            }
            travelsLiveData.postValue(listTravel)
        }
        travelsLiveData.observe(viewLifecycleOwner) { travels ->
            with(binding) {
                rvContent.apply {
                    adapter = TravelAdapter(travels) { travel ->
                        navigateToOrderActivity(travel)
                    }
                    layoutManager = LinearLayoutManager(requireContext())
                }
                if (travels.isEmpty()) {
                    txtMsg.visibility = View.VISIBLE
                }
                else {
                    txtMsg.visibility = View.GONE
                }
            }
        }
    }

}