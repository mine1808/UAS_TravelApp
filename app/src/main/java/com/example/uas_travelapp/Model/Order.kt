package com.example.uas_travelapp.Model

data class Order(
    var id: String = "",
    var title: String = "",
    var desc: String = "",
    var user_email: String = "",
    var id_travel: String = "",
    var selectedPaket: List<String> = listOf(),
    var date: String = "",
    var totalPrice: Int = 0
)
