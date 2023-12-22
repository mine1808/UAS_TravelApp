package com.example.uas_travelapp.Model

data class User(
    var nama: String = "",
    var username: String = "",
    val email: String = "",
    var password: String = "",
    var role: String = "user",
)
