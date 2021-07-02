package com.example.githubsearchusers.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubsearchusers.data.local.FavouriteUser
import com.example.githubsearchusers.data.local.FavouriteUserDao
import com.example.githubsearchusers.data.local.FavouriteUserDatabase

class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavouriteUserDao?
    private var favouriteUserDb: FavouriteUserDatabase?

    init {
        favouriteUserDb = FavouriteUserDatabase.getDatabase(application)
        userDao = favouriteUserDb?.favouriteUserDao()
    }

    fun getFavouriteUser(): LiveData<List<FavouriteUser>>? {
        return userDao?.getFavouriteUser()
    }
}