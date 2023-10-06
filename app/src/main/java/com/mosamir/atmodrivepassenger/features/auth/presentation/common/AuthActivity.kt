package com.mosamir.atmodrivepassenger.features.auth.presentation.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mosamir.atmodrivepassenger.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}