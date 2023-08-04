package com.ellies.gymsaround.gyms.domain

data class Gym(
    val id: Int,
    val name: String,
    val place: String,
    val isOpen: Boolean,
    val isFavourite: Boolean = false
)
