package com.app.veggiefood.model.request

data class PlaceOrderRequest(
    val u_id: String,
    val fname: String,
    val lname: String,
    val phone: String,
    val email: String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val subtotal: String,
    val final_price: String,
    val shipping_method: String,
    val transaction_id: String,
    val note: String,
    val cart: String
)
