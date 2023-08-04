package com.ellies.gymsaround.gyms.presentation.gymslist

import com.ellies.gymsaround.gyms.domain.Gym

data class GymsScreenState(
    val gyms: List<Gym>,
    val isLoading: Boolean,
    val error: String? = null,
)