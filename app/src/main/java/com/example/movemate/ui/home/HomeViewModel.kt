package com.example.movemate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movemate.R
import com.example.movemate.models.SearchHistory
import com.example.movemate.models.VehiclesOption

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val query = MutableLiveData<String>()

    val historyList = listOf(
        SearchHistory("Macbook pro M2", "#NE43857340857904", "Paris", "Morocco"),
        SearchHistory("Summer linen jacket", "#NEJ120089934127731", "Barcelona", "Paris"),
        SearchHistory("Tapered-fit jeans AW", "#NEJ3870264978649", "Colombia", "Paris"),
        SearchHistory("Slim fit jeans AW", "#NEJ20764978656659", "Bogota", "Dhaka"),
        SearchHistory("Office setup desk", "#NEJ3481470754963", "France", "Ghana"),
        SearchHistory("iPhone 19 pro Max", "#NEJ120089654357731", "Kampala", "Detroit"),
    )

    val vehicleList = listOf(
        VehiclesOption(R.drawable.ship, "Ocean freight", "International"),
        VehiclesOption(R.drawable.truck, "Cargo freight", "Reliable"),
        VehiclesOption(R.drawable.airplane, "Air freight", "International"),
        VehiclesOption(R.drawable.train, "Train freight", "Multi service"),
        VehiclesOption(R.drawable.drone, "Drone freight", "Live"),
        VehiclesOption(R.drawable.scooter, "Ford Explorer", "Local"),
        VehiclesOption(R.drawable.truck, "Ford Explorer", "Local"),
    )

}