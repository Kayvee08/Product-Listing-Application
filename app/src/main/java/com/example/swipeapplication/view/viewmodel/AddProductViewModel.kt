package com.example.swipeapplication.view.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeapplication.data.ProductData
import com.example.swipeapplication.data.Status
import com.example.swipeapplication.data.SubmitResponse
import com.example.swipeapplication.remote.ProductRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class AddProductViewModel(private val repository: ProductRepository) : ViewModel() {

    var productImage: Bitmap? = null
    var file: File? = null
    var multiPart: MultipartBody.Part? = null

    private val _products = MutableLiveData<SubmitResponse>()
    val products: LiveData<SubmitResponse> = _products

    fun submit(product: ProductData) {
        viewModelScope.launch {
            try {
                _products.postValue(SubmitResponse(Status.LOADING, "Please Wait"))
                var result = repository.addProduct(product = product)
                Log.d("kvresponse", "$result")
                _products.postValue(SubmitResponse(Status.SUCCESS, result.message ?: "SUCCESS"))
            } catch (e: Exception) {
                Log.d("kvresponse", e.toString())
                _products.postValue(SubmitResponse(Status.ERROR, e.toString()))
            }
        }
    }

}