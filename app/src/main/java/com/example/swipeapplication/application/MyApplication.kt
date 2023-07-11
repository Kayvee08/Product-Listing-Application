package com.example.swipeapplication.application

import android.app.Application
import com.example.swipeapplication.di.NetworkModule
import com.example.swipeapplication.di.RepositoryModule
import com.example.swipeapplication.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MyApplication)
            modules(NetworkModule)
            modules(RepositoryModule)
            modules(ViewModelModule)
        }
    }

}