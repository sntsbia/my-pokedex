package com.sntsb.mypokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyPokedexApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}