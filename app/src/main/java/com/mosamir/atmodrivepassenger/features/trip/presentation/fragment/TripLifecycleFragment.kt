package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentTripLifecycleBinding
import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TripLifecycleFragment : Fragment() {


    private var _binding: FragmentTripLifecycleBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    var model = SharedViewModel()
    private val tripViewModel by viewModels<TripViewModel>()

    private var valueEventListener : ValueEventListener?= null
    private lateinit var database: DatabaseReference

    private var captainMobile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTripLifecycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        database = Firebase.database.reference

        tripViewModel.getCaptainDetails(Constants.tripId)

        listenerOnTrip()
        observeOnCaptainDetails()
        onClick()

    }

    private fun onClick(){

        binding.imgCallCaptain.setOnClickListener {
            val phoneNumber = Uri.parse("tel:$captainMobile")
            val callIntent = Intent(Intent.ACTION_DIAL, phoneNumber)
            startActivity(callIntent)
        }

    }

    private fun listenerOnTrip(){
        valueEventListener =  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val tripStats = snapshot.getValue(String::class.java)

                if (tripStats == null){
                    model.setRequestTrip(false)
                }else if(tripStats == "start_trip"){

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.child("trips").child(Constants.tripId.toString()).child("status")
            .addValueEventListener(valueEventListener!!)
    }

    private fun observeOnCaptainDetails(){
        lifecycleScope.launch {
            tripViewModel.getCaptainDetailsResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        val data = networkState.data as IResult<CaptainDetailsResponse>
                        updateUi(data.getData()?.data!!)
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                    }
                    else -> {}
                }
            }
        }
    }

    private fun updateUi(data:CaptainData){
        binding.apply {

            tvCaptainName.text = data.full_name

        }
        captainMobile = data.mobile
        Glide.with(this)
            .load(Constants.BASE_Image_URL+data.avatar)
            .placeholder(R.drawable.captain)
            .into(binding.imgTripCaptain)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        database.child("trips").child(Constants.tripId.toString()).child("status")
            .removeEventListener(valueEventListener!!)
    }
}