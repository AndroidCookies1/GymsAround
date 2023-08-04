package com.ellies.gymsaround.gyms.domain

import com.ellies.gymsaround.gyms.data.GymsRepository
import javax.inject.Inject

class ToggleFavouriteStateUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase
) {
    suspend operator fun invoke(id: Int, oldState: Boolean): List<Gym> {
        val newState = oldState.not()
        gymsRepository.toggleFavouriteGym(id, newState)
        return getSortedGymsUseCase()
    }
}