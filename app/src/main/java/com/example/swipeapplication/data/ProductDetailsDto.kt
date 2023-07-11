package com.example.swipeapplication.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailsDto(
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("price")
    var price: Double? = null,
    @SerializedName("product_name")
    var productName: String? = null,
    @SerializedName("product_type")
    var productType: String? = null,
    @SerializedName("tax")
    var tax: Double? = null
) : Parcelable

@Parcelize
data class ProductListWrapper(
    var products: List<ProductDetailsDto>? = null,
    var loading: Boolean = false
) : Parcelable
