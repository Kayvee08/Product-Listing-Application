package com.example.swipeapplication.service

import com.example.swipeapplication.data.AddProductResponse
import com.example.swipeapplication.data.ProductDetailsDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MyApplicationService {

    @GET("get")
    suspend fun getProducts(): List<ProductDetailsDto>

    @Multipart
    @POST("add")
    suspend fun addProduct(
        //@Body productData: RequestBody,
        @PartMap partMap: MutableMap<String,RequestBody>,
        @Part file: Array<MultipartBody.Part?>
    ):AddProductResponse

    @Multipart
    @POST("add")
    suspend fun addAProduct(
        //@Body productData: RequestBody,
        @Part("product_name") prodName: RequestBody,
        @Part("product_type") prodType: RequestBody,
        @Part("price") prodPrice: RequestBody,
        @Part("tax") prodTax: RequestBody,
        @Part file: MultipartBody.Part?
    ):AddProductResponse

}