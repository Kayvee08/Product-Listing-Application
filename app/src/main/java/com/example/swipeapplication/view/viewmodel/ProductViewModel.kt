package com.example.swipeapplication.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeapplication.data.ProductListWrapper
import com.example.swipeapplication.remote.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<ProductListWrapper>()
    val products: LiveData<ProductListWrapper> = _products

    fun fetchProducts() {
        _products.postValue(ProductListWrapper(null,true))
        viewModelScope.launch {
            try {
                val products = repository.getAllProducts()
                _products.postValue(ProductListWrapper(products, false))
            }catch (e:Exception){
                _products.postValue(ProductListWrapper(arrayListOf(),false))
            }
        }
    }


}