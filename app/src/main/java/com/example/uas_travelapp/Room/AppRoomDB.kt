package com.example.uas_travelapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.uas_travelapp.Model.Favorite
import com.example.uas_travelapp.Model.PendingAction

@Database(entities = [Favorite::class, PendingAction::class], version = 2, exportSchema = false)
abstract class AppRoomDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao?
    abstract fun pendingActionDao(): PendingActionDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDB? = null
        fun getDatabase(context: Context): AppRoomDB? {
            if (INSTANCE == null) {
                synchronized(AppRoomDB::class.java) {
                    INSTANCE = databaseBuilder(context.applicationContext, AppRoomDB::class.java, "uas_travel_db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}