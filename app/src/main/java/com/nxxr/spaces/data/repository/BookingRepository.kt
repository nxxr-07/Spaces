package com.nxxr.spaces.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nxxr.spaces.data.model.Seat
import com.nxxr.spaces.data.model.Booking
import kotlinx.coroutines.tasks.await

class BookingRepository {

    private val db = FirebaseFirestore.getInstance()
    private val seatsRef = db.collection("seats")
    private val bookingsRef = db.collection("bookings")

    // 🔸 Get all seats
    suspend fun getAllSeats(): List<Seat> {
        return try {
            seatsRef.get().await().documents.mapNotNull {
                it.toObject(Seat::class.java)?.copy(id = it.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // 🔸 Book a seat
    suspend fun bookSeat(seatId: String, userId: String): Boolean {
        return try {
            val bookingTime = System.currentTimeMillis()

            // 1. Update the seat status
            seatsRef.document(seatId).update(
                mapOf(
                    "isBooked" to true,
                    "bookedBy" to userId,
                    "bookedTime" to bookingTime
                )
            ).await()

            // 2. Create a booking record
            val booking = Booking(
                bookingId = bookingsRef.document().id,
                seatId = seatId,
                userId = userId,
                timestamp = bookingTime
            )

            bookingsRef.document(booking.bookingId).set(booking).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // 🔸 Cancel booking (manual or automatic)
    suspend fun cancelBooking(seatId: String): Boolean {
        return try {
            seatsRef.document(seatId).update(
                mapOf(
                    "isBooked" to false,
                    "bookedBy" to null,
                    "bookedTime" to 0L
                )
            ).await()

            val bookings = bookingsRef.whereEqualTo("seatId", seatId).get().await()
            for (doc in bookings.documents) {
                doc.reference.update("isCheckedIn", false).await()
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    // 🔸 Check-In by user
    suspend fun checkIn(bookingId: String): Boolean {
        return try {
            bookingsRef.document(bookingId).update("isCheckedIn", true).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

