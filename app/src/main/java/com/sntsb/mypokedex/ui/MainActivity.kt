package com.sntsb.mypokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.sntsb.mypokedex.R
import com.sntsb.mypokedex.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.setDisplayShowTitleEnabled(false)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myViewModel = viewModel

        try {

            val splashScreen = installSplashScreen()

            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Personalizar a animação de saída

                splashScreenView.iconView.animate().setDuration(2500).alpha(0f).withEndAction {
                    splashScreenView.remove()
                }.start()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }




    }
}
