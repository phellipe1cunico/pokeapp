package com.example.pokeapp

import android.app.Application
import com.example.pokeapp.di.AppContainer
import com.example.pokeapp.di.AppContainerInterface


class MainApplication : Application() {


    lateinit var container: AppContainerInterface
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}