package com.example.uas_travelapp.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.uas_travelapp.Activity.DashboardUserActivity
import com.example.uas_travelapp.Activity.HomeActivity
import com.example.uas_travelapp.Model.User
import com.example.uas_travelapp.R
import com.example.uas_travelapp.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {
    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    private val firestore = FirebaseFirestore.getInstance()
    private val usersRefColl = firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val home = activity as HomeActivity

        with(binding) {
            btnRegister.setOnClickListener {
                if (allFieldsFilled()) {
                    val nama = editName.text.toString()
                    val username = editUsername.text.toString()
                    val email = editEmail.text.toString()
                    val password = editPassword.text.toString()

                    usersRefColl.document(email).get().addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            Toast.makeText(requireContext(), "Email sudah terdaftar!", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val user = User(nama = nama, username = username, email = email, password = password, role = "user")
                            registerNewUser(user)
                        }
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Mohon isi semua data!", Toast.LENGTH_SHORT).show()
                }
            }
            txtHaveAccount.setOnClickListener {
                home.viewPager2.setCurrentItem(0)
            }
        }
        return binding.root
    }

    private fun allFieldsFilled(): Boolean {
        return binding.editName.text.isNotEmpty() && binding.editEmail.text.isNotEmpty() &&
                binding.editUsername.text.isNotEmpty() && binding.editPassword.text.isNotEmpty()
    }

    private fun registerNewUser(user: User) {
        usersRefColl.document(user.email).set(user).addOnSuccessListener {
            val sharedPrefs = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("email", user.email)
            editor.putString("username", user.username)
            editor.putString("role", user.role)
            editor.apply()

            val intent = Intent(requireContext(), DashboardUserActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Log.d("User Register", "Failed to registering new user: $it")
        }
    }
}