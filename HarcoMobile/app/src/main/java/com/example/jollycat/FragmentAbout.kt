package com.example.jollycat

import android.Manifest
import android.content.pm.PackageManager
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jollycat.databinding.FragmentAboutBinding
import com.example.jollycat.databinding.FragmentCartBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FragmentAbout : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        // Initialize UI components
        val txtPlace = binding.txtPlace
//        val btnBack = binding.btnBack

        txtPlace.text = "Harco Mobile"


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker for JollyCat's Store and move the camera
        val position = LatLng(-6.1389438110092, 106.82842125254977)
        val option = MarkerOptions().position(position).title("Harco Store")
        mMap.addMarker(option)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f))
    }
}
