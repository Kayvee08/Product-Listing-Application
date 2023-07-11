package com.example.swipeapplication.di

import com.example.swipeapplication.view.viewmodel.AddProductViewModel
import com.example.swipeapplication.view.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { ProductViewModel(repository = get()) }
    viewModel { AddProductViewModel(repository = get()) }
}