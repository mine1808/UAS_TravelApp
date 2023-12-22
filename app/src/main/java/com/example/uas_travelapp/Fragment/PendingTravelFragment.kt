package com.example.uas_travelapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uas_travelapp.Activity.DashboardAdminActivity
import com.example.uas_travelapp.Adapter.PendingActionAdapter
import com.example.uas_travelapp.databinding.FragmentPendingTravelBinding


class PendingTravelFragment : Fragment()  {
    private val binding by lazy {
        FragmentPendingTravelBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAllPendingActions()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getAllPendingActions()
    }

    private fun getAllPendingActions() {
        DashboardAdminActivity.PendingAction.allPendingActions.observe(viewLifecycleOwner) { actions ->
            if (actions.size > 0) {
                binding.txtMsg.visibility = View.GONE
                binding.rvContent.visibility = View.VISIBLE
                binding.rvContent.apply {
                    adapter = PendingActionAdapter(actions)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
            else {
                binding.txtMsg.visibility = View.VISIBLE
                binding.rvContent.visibility = View.GONE
            }
        }
    }

}