package com.example.wancommerce.di.remote

import com.example.wancommerce.data.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/products?limit=10&")
    fun getProductDummy(@Query("skip") skip: Int): Call<ResponseModel>

    @GET("/products/search")
    fun searchProduct(@Query("q") q: String): Call<ResponseModel>

    @GET("products/categories")
    fun getCategory() : Call<Any>

    @GET("products/category/{categori}")
    fun getProductCategory(@Path("categori") categori: String) : Call<ResponseModel>

}