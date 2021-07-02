package com.example.githubsearchusers.api

import com.example.githubsearchusers.data.model.DetailUserResponse
import com.example.githubsearchusers.data.model.Repository
import com.example.githubsearchusers.data.model.User
import com.example.githubsearchusers.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: token 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")
    fun getRepositories(
        @Path("username") username: String
    ): Call<ArrayList<Repository>>

    //14:41

    /*@GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
  */
}