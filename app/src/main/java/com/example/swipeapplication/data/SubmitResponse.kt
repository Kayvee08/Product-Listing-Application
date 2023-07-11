package com.example.swipeapplication.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubmitResponse(
    val status: Status,
    val message: String
) : Parcelable

enum class Status {
    SUCCESS, ERROR, LOADING
}
