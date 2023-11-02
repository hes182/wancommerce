package com.example.wancommerce.di.remote

import com.example.wancommerce.data.ResponseModel
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val BASE_URL = "https://dummyjson.com"
    private var apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun callGetProductLisReq(skip: Int, callback: Callback<ResponseModel>) {
        apiService.getProductDummy(skip).enqueue(callback)
    }

    fun callSearchProductList(q: String?, callback: Callback<ResponseModel>) {
        apiService.searchProduct(q.toString()).enqueue(callback)
    }

    fun getCategory(callback: Callback<Any>) {
        apiService.getCategory().enqueue(callback)
    }

    fun getProductCategory(category: String, callback: Callback<ResponseModel>) {
        apiService.getProductCategory(category).enqueue(callback)
    }
}