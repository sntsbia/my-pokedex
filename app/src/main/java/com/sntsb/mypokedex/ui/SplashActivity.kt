package com.sntsb.mypokedex.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.sntsb.mypokedex.databinding.ActivitySplashBinding

class SplashActivity : ComponentActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.initApp(this@SplashActivity)

    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}
