package com.app.veggiefood.model.request

data class SignupRequest(
    val fname: String,
    val lname: String,
    val email: String,
    val phone: String,
    val password1: String,
    val password2: String,
    val tnc: String
)