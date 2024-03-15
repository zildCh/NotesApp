package com.github.zottaa.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = viewModel(MainViewModel::class.java)

        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, binding.container.id)
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T =
        (application as ProvideViewModel).viewModel(clasz)

}