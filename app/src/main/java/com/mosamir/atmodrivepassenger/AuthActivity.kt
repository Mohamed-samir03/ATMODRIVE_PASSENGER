package com.mosamir.atmodrivepassenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mosamir.atmodrivepassenger.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}