package com.sntsb.mypokedex.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.sntsb.mypokedex.R
import com.sntsb.mypokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportActionBar?.hide()

        viewModel.texto.observe(this) { novoTexto ->
            Log.d("MainActivity", "Novo texto: $novoTexto")

        }
        viewModel.selected.observe(this) { novoTexto ->
            Log.d("MainActivity", "selected: $novoTexto")
        }

        binding.myViewModel = viewModel

        binding.cgFiltro.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.e(TAG, "onCreate: $checkedIds")
            if(checkedIds.isEmpty()) {

                viewModel.setSelected("")
            } else {

                findViewById<Chip>(checkedIds[0]).text?.let{
                    viewModel.setSelected(it.toString())
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressbar.visibility = View.VISIBLE

            Snackbar.make(binding.root, "Refresh", Snackbar.LENGTH_SHORT).show()
            viewModel.refresh()

            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressbar.visibility = View.GONE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}