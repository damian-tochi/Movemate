package com.example.movemate.ui.shipments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movemate.models.SearchHistory
import com.example.movemate.models.ShipmentsHistory

class ShipmentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Shipment Fragment"
    }
    val text: LiveData<String> = _text

    val historyList = listOf(
        ShipmentsHistory("in-progress","Arriving today!",  "#NE43857340857904", "Paris", "Morocco", "1400", "Sep 20,2023"),
        ShipmentsHistory("pending","Arriving today!",   "#NEJ1200899341277", "Barcelona","", "650", "Sep 20,2023"),
        ShipmentsHistory("pending","Arriving today!",  "#NEJ3870264978649", "Singapore", "", "650", "Sep 20,2023"),
        ShipmentsHistory("loading","Arriving today!",  "#NEJ20764978656659", "Bogota", "", "370", "Sep 20,2023"),
        ShipmentsHistory("loading","Arriving today!",   "#NEJ34814707549633", "France", "", "370", "Sep 20,2023"),
        ShipmentsHistory("cancelled","Arriving today!",  "#NEJ12008965435773", "Ghana", "", "1400", "Sep 20,2023"),
        ShipmentsHistory("in-progress","Arriving today!",  "#NE43857340857904", "Atlanta", "Morocco", "800", "Sep 20,2023"),
        ShipmentsHistory("in-progress","Arriving today!",   "#NEJ1200899341277", "Bangladesh","", "800", "Sep 20,2023"),
        ShipmentsHistory("completed","Arriving today!",  "#NEJ3870264978649", "Colombia", "", "650", "Sep 20,2023"),
        ShipmentsHistory("completed","Arriving today!",  "#NEJ20764978656659", "Bogota", "", "370", "Sep 20,2023"),
        ShipmentsHistory("completed","Arriving today!",   "#NEJ34814707549633", "France", "", "800", "Sep 20,2023"),
        ShipmentsHistory("pending","Arriving today!",  "#NEJ12008965435773", "Kampala", "", "1400", "Sep 20,2023"),
    )

    val tabTitles = listOf("All", "Completed", "In progress", "Pending order", "Cancelled")
}