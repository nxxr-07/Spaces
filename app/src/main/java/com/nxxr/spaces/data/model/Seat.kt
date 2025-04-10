package com.nxxr.spaces.data.model

data class Seat(
    val id: String = "",
    val isBooked: Boolean = false,
    val bookedBy: String? = null,
    val bookedTime: Long = 0L
)

