package com.app.veggiefood.model.request

data class CalculatePriceRequest(
    val variation_id: String,
    val product_id: String,
    val qty: String
)