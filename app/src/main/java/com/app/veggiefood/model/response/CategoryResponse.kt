package com.app.veggiefood.model.response

data class CategoryResponse(
    val status:Boolean,
    val data:ArrayList<CategoryModel>
)
data class CategoryModel(
    val id:String,
    val name:String,
    val url:String,
    val image:String,
    val status:String
)