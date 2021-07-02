package com.example.githubsearchusers.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubsearchusers.api.RetrofitClient
import com.example.githubsearchusers.data.local.FavouriteUser
import com.example.githubsearchusers.data.local.FavouriteUserDao
import com.example.githubsearchusers.data.local.FavouriteUserDatabase
import com.example.githubsearchusers.data.model.DetailUserResponse
import com.example.githubsearchusers.data.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavouriteUserDao?
    private var favouriteUserDb: FavouriteUserDatabase?

    init {
        favouriteUserDb = FavouriteUserDatabase.getDatabase(application)
        userDao = favouriteUserDb?.favouriteUserDao()
    }

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : retrofit2.Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    val listRepositories = MutableLiveData<ArrayList<Repository>>()

    fun setListRepositories(username: String){
        RetrofitClient.apiInstance
            .getRepositories(username)
            .enqueue(object : retrofit2.Callback<ArrayList<Repository>>{
                override fun onResponse(
                    call: Call<ArrayList<Repository>>,
                    response: Response<ArrayList<Repository>>
                ) {
                    if (response.isSuccessful){
                        listRepositories.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repository>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListRepositories(): LiveData<ArrayList<Repository>>{
        return listRepositories
    }

    fun addToFavourite(username: String, id: Int, avatarUrl:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavouriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavourite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavourite(id)
        }
    }
}