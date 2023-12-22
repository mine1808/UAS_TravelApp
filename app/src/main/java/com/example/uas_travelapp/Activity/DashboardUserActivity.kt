package com.example.uas_travelapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uas_travelapp.Activity.HomeActivity
import com.example.uas_travelapp.Model.Favorite
import com.example.uas_travelapp.R
import com.example.uas_travelapp.Room.AppRoomDB
import com.example.uas_travelapp.Room.FavoriteDao
import com.example.uas_travelapp.databinding.ActivityDashboardUserBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DashboardUserActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDashboardUserBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var favorites: FavoriteDao
        lateinit var executorService: ExecutorService
        lateinit var loginEmail: String

        fun addFavorite(favorite: Favorite) {
            executorService.execute {
                favorites.insert(favorite)
            }
        }

        fun isFavorite(idTravel: String): Boolean {
            return executorService.submit<Boolean> {
                favorites.getUserFavListByTravel(idTravel, loginEmail).isNotEmpty()
            }.get()
        }

        fun deleteFavByIdTravel(idTravel: String) {
            executorService.execute {
                favorites.deleteUserFavByTravel(idTravel, loginEmail)
            }
        }

        fun getFavbyTravel(idTravel: String): Favorite {
            return executorService.submit<Favorite> {
                favorites.getUserFavByTravel(idTravel, loginEmail)
            }.get()
        }

        fun updateFavorite(favorite: Favorite) {
            executorService.execute {
                favorites.update(favorite)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPrefs = getSharedPreferences("UserData", MODE_PRIVATE)
        if (!sharedPrefs.contains("email")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginEmail = sharedPrefs.getString("email", "")!!

        executorService = Executors.newSingleThreadExecutor()
        val db = AppRoomDB.getDatabase(this)
        favorites = db!!.favoriteDao()!!

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomnavView.setupWithNavController(navController)
    }
}