package com.example.githubsearchusers.data.model

data class DetailUserResponse (
    val login : String,
    val id : Int,
    val avatar_url : String,
    val repos_url : String,
    val name: String,
    val following: Int,
    val followers: Int
)