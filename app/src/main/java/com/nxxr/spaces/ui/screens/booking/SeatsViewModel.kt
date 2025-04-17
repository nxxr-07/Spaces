package com.nxxr.spaces.ui.screens.booking

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nxxr.spaces.data.model.Seat
import com.nxxr.spaces.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SeatsViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val seatsCollection = firestore.collection("seats")

    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: StateFlow<List<Seat>> = _seats

    val bookingRepository = BookingRepository()

    init {
        bookingRepository.initializeSeatsIfNotPresent()
        listenToSeats()
    }

    private fun listenToSeats() {
        seatsCollection.addSnapshotListener { snapshot, _ ->
            val seatList = snapshot?.toObjects(Seat::class.java)?.sortedBy { it.number } ?: emptyList()
            _seats.value = seatList
        }
    }
}

