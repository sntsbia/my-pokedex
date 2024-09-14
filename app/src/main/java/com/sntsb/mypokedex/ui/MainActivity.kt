package com.sntsb.mypokedex.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sntsb.mypokedex.R
import com.sntsb.mypokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        actionBar?.setDisplayShowTitleEnabled(false)

        viewModel.texto.observe(this) { novoTexto ->
            Log.d("MainActivity", "Novo texto: $novoTexto")
            binding.tvSplash.invalidate()
        }

        binding.myViewModel = viewModel

        viewModel.setTexto("Olá Mundo")

        binding.btnTexto.setOnClickListener {
            viewModel.setTexto()
        }

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}