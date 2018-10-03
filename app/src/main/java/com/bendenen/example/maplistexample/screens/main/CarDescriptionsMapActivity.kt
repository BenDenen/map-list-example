package com.bendenen.example.maplistexample.screens.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bendenen.example.maplistexample.R
import com.bendenen.example.maplistexample.models.CarDescription
import com.bendenen.example.maplistexample.screens.list.CarDescriptionsListActivity
import com.bendenen.example.maplistexample.screens.main.viewmodel.CarDescriptionsMapViewModel
import com.bendenen.example.maplistexample.utils.ResourceObserver
import com.bendenen.example.maplistexample.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_map.progressbar
import kotlinx.android.synthetic.main.activity_map.show_list_button
import javax.inject.Inject

class CarDescriptionsMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnMarkerClickListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var carDescriptionsViewModel: CarDescriptionsMapViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        carDescriptionsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CarDescriptionsMapViewModel::class.java)

        show_list_button.setOnClickListener { startActivity(CarDescriptionsListActivity.createIntent(this)) }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.setOnMapLoadedCallback(this)
        this.googleMap.setOnMarkerClickListener(this)
    }

    override fun onMapLoaded() {
        carDescriptionsViewModel.carDescriptions.observe(this, ResourceObserver("CarDescriptionsMapActivity",
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::showMarkers,
                onError = ::showErrorMessage))
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

    // Private methods

    private fun constructMarkerOptions(carDescription: CarDescription): MarkerOptions {
        val point = LatLng(carDescription.latitude, carDescription.longitude)
        val icon = BitmapDescriptorFactory.fromBitmap(utils.getBitmap(R.drawable.ic_map_pin))
        //snippet to distinguish markers within UiAutomator
        return MarkerOptions().snippet("ModelName:${carDescription.modelName}").position(point).title(carDescription.name).icon(icon)
    }

    private fun showMarkers(carDescriptionList: List<CarDescription>) {
        googleMap.clear()
        val builder = LatLngBounds.Builder()
        carDescriptionList.map {
            Pair(it, googleMap.addMarker(constructMarkerOptions(it)))
        }.map {
            it.second.tag = it.first
            it.second
        }.map {
            builder.include(it.position)
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 40))
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressbar.visibility = View.GONE
    }


}
