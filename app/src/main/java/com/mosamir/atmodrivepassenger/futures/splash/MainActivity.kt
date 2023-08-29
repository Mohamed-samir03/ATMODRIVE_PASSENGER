package com.mosamir.atmodrivepassenger.futures.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mosamir.atmodrivepassenger.databinding.ActivityMainBinding
import com.mosamir.atmodrivepassenger.futures.auth.presentation.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(3000)

                val intent = Intent(applicationContext, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}