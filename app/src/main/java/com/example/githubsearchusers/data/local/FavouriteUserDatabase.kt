package com.example.githubsearchusers.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteUser::class],
    version = 1
)
abstract class FavouriteUserDatabase: RoomDatabase(){
    companion object{
        var INSTANCE : FavouriteUserDatabase? = null

        fun getDatabase(context: Context): FavouriteUserDatabase?{
            if(INSTANCE == null){
                synchronized(FavouriteUserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavouriteUserDatabase::class.java, "user database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favouriteUserDao(): FavouriteUserDao
}