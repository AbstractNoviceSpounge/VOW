package com.app.veggiefood.model.request

data class AddEditCartRequest(
    val cart_id: String,
    val variation_id: String,
    val user_id: String,
    val product_id: String,
    val price: String,
    val qty: String
)