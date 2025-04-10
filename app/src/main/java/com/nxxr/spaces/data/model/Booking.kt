package com.nxxr.spaces.data.model

data class Booking(
    val bookingId: String = "",
    val seatId: String = "",
    val userId: String = "",
    val timestamp: Long = 0L,
    val isCheckedIn: Boolean = false
)

