package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mosamir.atmodrivepassenger.databinding.ActivityTripBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onDestroy() {
        super.onDestroy()
    }

}