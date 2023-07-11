package com.example.swipeapplication.di

import com.example.swipeapplication.remote.ProductRepository
import com.example.swipeapplication.remote.ProductRepositoryImpl
import org.koin.dsl.module

val RepositoryModule = module{
        single<ProductRepository> {
            ProductRepositoryImpl(service =  get())
        }
}