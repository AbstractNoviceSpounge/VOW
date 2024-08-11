package com.app.veggiefood.model.request

data class ProfileRequest(
    val fname: String,
    val lname: String,
    val email: String,
    val phone: String, val address: String,
    val city: String,
    val state: String,
    val pincode: String
)