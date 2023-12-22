package com.example.uas_travelapp.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.uas_travelapp.Model.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)
    @Update
    fun update(favorite: Favorite)
    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorites WHERE user_email = :userEmail ORDER BY id ASC")
    fun getUserFavorites(userEmail: String): LiveData<List<Favorite>>
    @Query("SELECT * FROM favorites WHERE id_travel = :idTravel AND user_email = :userEmail")
    fun getUserFavListByTravel(idTravel: String, userEmail: String): List<Favorite>
    @Query("DELETE FROM favorites WHERE id_travel = :idTravel AND user_email = :userEmail")
    fun deleteUserFavByTravel(idTravel: String, userEmail: String)
    @Query("SELECT * FROM favorites WHERE id_travel = :idTravel AND user_email = :userEmail LIMIT 1")
    fun getUserFavByTravel(idTravel: String, userEmail: String): Favorite
}