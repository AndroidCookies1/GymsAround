package com.ellies.gymsaround.gyms.data

import com.ellies.gymsaround.gyms.data.di.IODispatcher
import com.ellies.gymsaround.gyms.data.local.GymsDao
import com.ellies.gymsaround.gyms.data.local.LocalGym
import com.ellies.gymsaround.gyms.data.local.LocalGymFavouriteState
import com.ellies.gymsaround.gyms.data.remote.GymsApiService
import com.ellies.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor (
    private val apiService: GymsApiService,
    private val gymsDao: GymsDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun loadGyms() = withContext(dispatcher) {
        try {
            updateLocalDatabase()
        } catch (ex: Exception) {
            if(gymsDao.getAll().isEmpty()) {
                throw Exception("Something went wrong. No data was found, try connecting to internet.")
            }
        }
    }

    suspend fun getGyms(): List<Gym> {
        return withContext(dispatcher) {
            return@withContext gymsDao.getAll().map {
                Gym(it.id, it.name, it.place, it.isOpen, it.isFavourite)
            }
        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()

        val favouriteGymsList = gymsDao.getFavouriteGyms()

        gymsDao.addAll(
            gyms.map {
                LocalGym(it.id, it.name, it.place, it.isOpen,)
            }
        )

        gymsDao.updateAll(
            favouriteGymsList.map { LocalGymFavouriteState(id = it.id, true) }
        )
    }

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) = withContext(
        dispatcher) {
        gymsDao.update(
            LocalGymFavouriteState(
                id = gymId,
                isFavourite = state
            )
        )
        return@withContext gymsDao.getAll()
    }

}