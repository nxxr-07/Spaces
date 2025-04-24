package com.nxxr.spaces.ui.screens.home

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.nxxr.spaces.data.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _currentBooking = MutableStateFlow<Booking?>(null)
    val currentBooking: StateFlow<Booking?> get() = _currentBooking

    private val _occupiedSeatsCount = MutableStateFlow(0)
    val occupiedSeatsCount: StateFlow<Int> get() = _occupiedSeatsCount

    fun fetchHomeData(userId: String) {
        fetchCurrentBooking(userId)
        fetchOccupiedSeats()
    }

    private fun fetchCurrentBooking(userId: String) {
        val now = System.currentTimeMillis()

        firestore.collection("bookings")
            .whereEqualTo("userId", userId)
            .whereGreaterThan("endTime", now)
            .get()
            .addOnSuccessListener { snapshot ->
                val booking = snapshot.documents.firstOrNull()?.toObject(Booking::class.java)
                _currentBooking.value = booking
                println("Booking fetched: $booking")
            }
            .addOnFailureListener {
                println("Booking fetch failed: ${it.message}")
            }

    }

    private fun fetchOccupiedSeats() {
        firestore.collection("seats")
            .whereEqualTo("booked", true)
            .get()
            .addOnSuccessListener { snapshot ->
                _occupiedSeatsCount.value = snapshot.size()
            }
    }
}
