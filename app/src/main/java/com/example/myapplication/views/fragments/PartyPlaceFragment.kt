package com.example.myapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPartyPlaceBinding
import com.example.myapplication.viewmodels.PartyPlaceViewModel
import com.example.myapplication.viewmodels.PartyPlaceViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class PartyPlaceFragment: Fragment() {
    @Inject
    lateinit var mFactory: PartyPlaceViewModelFactory

    private lateinit var mViewModel: PartyPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        setHasOptionsMenu(true)
        val arguments : PartyPlaceFragmentArgs by navArgs()
        mFactory.placeId = arguments.partyId
        mViewModel = ViewModelProvider(this, mFactory).get(PartyPlaceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPartyPlaceBinding.inflate(inflater, container, false)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, findNavController());
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?

        supportMapFragment?.getMapAsync {map ->
                mViewModel.dataLoadingCompete.observe(viewLifecycleOwner, Observer { adressWithCoordinates ->
                        val sydney = LatLng(adressWithCoordinates.second.latitude, adressWithCoordinates.second.longitude)
                        map.addMarker(
                            MarkerOptions().position(sydney)
                                .title(adressWithCoordinates.first)
                        )
                        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        map.setMinZoomPreference(15f)
                })

            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}