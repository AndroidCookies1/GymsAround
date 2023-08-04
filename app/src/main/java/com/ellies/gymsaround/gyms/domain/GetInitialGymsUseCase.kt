package com.ellies.gymsaround.gyms.domain

import com.ellies.gymsaround.gyms.data.GymsRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase
) {
    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}