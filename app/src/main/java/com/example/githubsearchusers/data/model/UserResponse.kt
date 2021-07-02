package com.example.githubsearchusers.data.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class UserResponse (
    val items: ArrayList<User>)
