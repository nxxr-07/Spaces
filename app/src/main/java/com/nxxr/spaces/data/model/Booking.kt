package com.nxxr.spaces.data.model

data class Booking(
    val bookingId: String = "",
    val status: String = "active",
    val seatId: String = "",
    val userId: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val isCheckedIn: Boolean = false
)

