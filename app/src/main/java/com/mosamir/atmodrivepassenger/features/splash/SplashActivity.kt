package com.mosamir.atmodrivepassenger.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mosamir.atmodrivepassenger.databinding.ActivitySplashBinding
import com.mosamir.atmodrivepassenger.features.auth.presentation.common.AuthActivity
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripActivity
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(3000)

                val token  = SharedPreferencesManager(this@SplashActivity).getString(Constants.REMEMBER_TOKEN_PREFS)

                if (token.isNullOrBlank()){
                    val intent = Intent(applicationContext, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(applicationContext, TripActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }

    }
}