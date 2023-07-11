package com.example.swipeapplication.remote

import com.example.swipeapplication.data.AddProductResponse
import com.example.swipeapplication.data.ProductData
import com.example.swipeapplication.data.ProductDetailsDto

interface ProductRepository {
    suspend fun getAllProducts():List<ProductDetailsDto>
    suspend fun addProduct(product: ProductData): AddProductResponse
}