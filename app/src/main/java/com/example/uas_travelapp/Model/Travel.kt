package com.example.uas_travelapp.Model

data class Travel(
    var id: String = "",
    var title: String = "",
    var asal: String = "",
    var tujuan: String = "",
    var hargaEkonomi: Int = 0,
    var hargaBisnis: Int = 0,
    var hargaEksekutif: Int = 0,
)
