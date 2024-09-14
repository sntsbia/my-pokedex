package com.sntsb.mypokedex.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {

    private var iniciado = false

    fun initApp(activity: ComponentActivity) {
        if (!iniciado) {

            Handler(Looper.getMainLooper()).postDelayed({
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            }, 2500)

            iniciado = true
        }
    }
}