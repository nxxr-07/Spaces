package com.nxxr.spaces.data.model

data class Seat(
    val id: String = "",
    val number: Int = 0,
    val booked: Boolean = false,
    val bookedBy: String? = null,
    val bookedTime: Long = 0L,
    val bookedUntil: Long = 0L
)

