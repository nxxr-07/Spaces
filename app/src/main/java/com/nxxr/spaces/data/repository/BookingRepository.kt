package com.nxxr.spaces.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nxxr.spaces.data.model.Seat
import com.nxxr.spaces.data.model.Booking
import kotlinx.coroutines.tasks.await

class BookingRepository {
    private val firestore = Firebase.firestore
    private val seatsCollection = firestore.collection("seats")

    fun initializeSeatsIfNotPresent() {
        for (i in 1..38) {
            val seatId = "seat_$i"
            val seat = Seat(id = seatId, number = i)

            seatsCollection.document(seatId).get().addOnSuccessListener { doc ->
                if (!doc.exists()) {
                    seatsCollection.document(seatId).set(seat)
                }
            }
        }
    }

}

