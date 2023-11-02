package com.example.wancommerce.data

data class ResponseModel(
    val products : ArrayList<ProductModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
)