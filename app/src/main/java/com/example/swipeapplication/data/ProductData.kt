package com.example.swipeapplication.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import java.io.InputStream

@Parcelize
data class ProductData(
    val productName: String,
    val productType: String,
    val price: String,
    val tax: String,
    val image: @RawValue MultipartBody.Part? = null
) : Parcelable
