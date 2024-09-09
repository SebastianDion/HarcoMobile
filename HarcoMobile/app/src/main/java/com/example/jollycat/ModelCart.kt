package com.example.jollycat

data class Cart(
    val cartID: Int,
    val catID: String,
    val checkoutID: Double,
    val userID: Int,
    var quantity: Int,
)