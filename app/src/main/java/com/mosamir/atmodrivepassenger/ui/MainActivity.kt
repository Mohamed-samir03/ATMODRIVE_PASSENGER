package com.mosamir.atmodrivepassenger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mosamir.atmodrivepassenger.databinding.ActivityMainBinding
import com.mosamir.atmodrivepassenger.ui.auth.AuthActivity
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            delay(3000)

            val intent = Intent(applicationContext, AuthActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
}