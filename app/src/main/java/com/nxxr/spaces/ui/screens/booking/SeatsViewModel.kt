package com.nxxr.spaces.ui.screens.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nxxr.spaces.data.model.Booking
import com.nxxr.spaces.data.model.Seat
import com.nxxr.spaces.data.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class SeatsViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val seatsCollection = firestore.collection("seats")

    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: StateFlow<List<Seat>> = _seats

    private val _bookingMessage = MutableStateFlow<String?>(null)
    val bookingMessage: StateFlow<String?> = _bookingMessage

    private val bookingRepository = BookingRepository()

    init {
        bookingRepository.initializeSeatsIfNotPresent()
        listenToSeats()
    }

    private fun listenToSeats() {
        seatsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("SeatsViewModel", "Snapshot error: ", error)
                return@addSnapshotListener
            }

            val now = System.currentTimeMillis()

            val seatList = snapshot?.toObjects(Seat::class.java)?.map { seat ->
                if (seat.booked && seat.bookedUntil < now) {
                    // Booking expired, clean up
                    handleExpiredBooking(seat)
                    seat.copy(booked = false, bookedBy = null, bookedTime = 0L, bookedUntil = 0L)
                } else {
                    seat
                }
            }?.sortedBy { it.number } ?: emptyList()

            _seats.value = seatList
        }
    }



    fun bookSeat(seat: Seat, startTimeMillis: Long, endTimeMillis: Long) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val bookingsCollection = Firebase.firestore.collection("bookings")
        val now = System.currentTimeMillis()

        //Check if user already has an active booking
        bookingsCollection
            .whereEqualTo("userId", currentUserId)
            .whereEqualTo("checkedIn", false)
            .whereGreaterThan("endTime", now)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    // User already has a booking
                    Log.d("Booking", "User already has a booking")
                    _bookingMessage.value = "You already have an active booking!"
                    return@addOnSuccessListener
                }

        // Step 2: Proceed with booking
        val bookingId = UUID.randomUUID().toString()
        val booking = Booking(
            bookingId = bookingId,
            status = "Upcoming",
            seatId = seat.id,
            userId = currentUserId,
            startTime = startTimeMillis,
            endTime = endTimeMillis,
            isCheckedIn = false
        )

        Firebase.firestore.collection("bookings")
            .document(bookingId)
            .set(booking).addOnCompleteListener{
                _bookingMessage.value = "Seat booked successfully!"
            }

        val updatedSeat = seat.copy(
            booked = true,
            bookedBy = currentUserId,
            bookedTime = startTimeMillis,
            bookedUntil = endTimeMillis
        )

        Firebase.firestore.collection("seats")
            .document(seat.id)
            .set(updatedSeat)
        }
    }

    private fun handleExpiredBooking(seat: Seat) {
        val seatRef = seatsCollection.document(seat.id)

        // 1. Unbook the seat
        seatRef.update(
            mapOf(
                "booked" to false,
                "bookedBy" to null,
                "bookedTime" to 0L,
                "bookedUntil" to 0L
            )
        )

        // 2. Update associated expired booking (mark as not checked-in + expired)
        Firebase.firestore.collection("bookings")
            .whereEqualTo("seatId", seat.id)
            .whereEqualTo("isCheckedIn", false)
            .whereLessThan("endTime", System.currentTimeMillis())
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot?.documents?.forEach { doc ->
                    doc.reference.update(
                        mapOf(
                            "isCheckedIn" to false,
                            "status" to "expired"
                        )
                    )
                }
            }
    }

    fun resetBookingMessage() {
        _bookingMessage.value = null
    }
}


