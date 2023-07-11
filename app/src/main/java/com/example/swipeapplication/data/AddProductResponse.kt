package com.example.swipeapplication.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class AddProductResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("product_details")
    var productDetails: @RawValue Any? = null,
    @SerializedName("product_id")
    var productId: Int? = null,
    @SerializedName("success")
    var success: Boolean? = null
) : Parcelable

