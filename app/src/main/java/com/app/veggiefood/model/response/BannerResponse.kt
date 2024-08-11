package com.app.veggiefood.model.response

data class BannerResponse(
    val status: Boolean,
    val data: ArrayList<BannerModel>
)

data class BannerModel(
    val id: String,
    val heading: String,
    val subheading: String,
    val image_url: String
)