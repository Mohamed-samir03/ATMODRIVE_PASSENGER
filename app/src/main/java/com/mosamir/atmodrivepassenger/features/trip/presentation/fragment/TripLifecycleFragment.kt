package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.disable
import com.mosamir.atmodrivepassenger.util.enabled
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
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
        tripViewModel.getTripDetails(Constants.tripId)

        listenerOnTrip()
        observer()
        onClick()

    }

    private fun onClick(){

        binding.imgCallCaptain.setOnClickListener {
            val phoneNumber = Uri.parse("tel:$captainMobile")
            val callIntent = Intent(Intent.ACTION_DIAL, phoneNumber)
            startActivity(callIntent)
        }

        binding.btnCancelTrip.setOnClickListener {
            cancelTripDialog()
        }

    }

    private fun cancelTripDialog(){

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cancel Trip")
        builder.setMessage("Do you want to cancel the trip?")

        builder.setPositiveButton("Yes") { dialog, which ->
            tripViewModel.cancelTrip(Constants.tripId)
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun listenerOnTrip(){
        valueEventListener =  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val tripStats = snapshot.getValue(String::class.java)

                when (tripStats) {
                    null -> {
                        model.setRequestTrip(false)
                    }
                    "accepted" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain accepted."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                        binding.imgCallCaptain.enabled()
                    }
                    "on_the_way" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain is on the way to you."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                        binding.imgCallCaptain.enabled()
                    }
                    "arrived" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain is arrived to pickup."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.success))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                        binding.imgCallCaptain.enabled()
                    }
                    "start_trip" -> {
                        binding.tvTripStatus.apply {
                            text = "Trip running"
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            disable()
                            setBackgroundResource(R.drawable.background_disable)
                            setTextColor(Color.parseColor("#D6E2ED"))
                        }
                        binding.imgCallCaptain.disable()
                    }
                    "pay" -> {
                        binding.tvTripStatus.apply {
                            text = "You are arrived to drop off! 🎉🎉"
                            setBackgroundColor(ContextCompat.getColor(context, R.color.success))
                        }
                        binding.btnCancelTrip.apply {
                            disable()
                            setBackgroundResource(R.drawable.background_disable)
                            setTextColor(Color.parseColor("#D6E2ED"))
                        }
                        binding.imgCallCaptain.disable()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.child("trips").child(Constants.tripId.toString()).child("status")
            .addValueEventListener(valueEventListener!!)
    }

    private fun observer(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tripViewModel.getCaptainDetailsResult.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            val data = networkState.data as IResult<CaptainDetailsResponse>
                            updateCaptainUi(data.getData()?.data!!)
                        }

                        NetworkState.Status.FAILED -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            showToast(networkState.msg.toString())
                        }

                        NetworkState.Status.RUNNING -> {
                            binding.tripCycleProgressBar.visibilityVisible()
                        }

                        else -> {}
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tripViewModel.tripDetailsResult.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            val data = networkState.data as IResult<TripDetailsResponse>
                            updateCarUi(data.getData()?.data!!)
                        }

                        NetworkState.Status.FAILED -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            showToast(networkState.msg.toString())
                        }

                        NetworkState.Status.RUNNING -> {
                            binding.tripCycleProgressBar.visibilityVisible()
                        }

                        else -> {}
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tripViewModel.cancelTripResult.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            val data = networkState.data as IResult<CancelTripResponse>
                            showToast(data.getData()?.message!!)
                        }

                        NetworkState.Status.FAILED -> {
                            binding.tripCycleProgressBar.visibilityGone()
                            showToast(networkState.msg.toString())
                        }

                        NetworkState.Status.RUNNING -> {
                            binding.tripCycleProgressBar.visibilityVisible()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun updateCaptainUi(data:CaptainData){
        binding.apply {
            tvCaptainName.text = data.full_name
            tvTripCaptainPrice.text = data.estimate_cost.toString()+" EGP"
        }
        captainMobile = data.mobile
        Glide.with(this)
            .load(Constants.BASE_Image_URL+data.avatar)
            .placeholder(R.drawable.captain)
            .into(binding.imgTripCaptain)
    }

    private fun updateCarUi(data:TripDetailsData){
        binding.apply {
            tvCarBrand.text = data.car_brand
            tvCarModel.text = data.car_model
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database.child("trips").child(Constants.tripId.toString()).child("status")
            .removeEventListener(valueEventListener!!)
        _binding = null
    }
}