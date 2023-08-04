package com.ellies.gymsaround.gyms.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ellies.gymsaround.gyms.data.remote.GymsApiService
import com.ellies.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val state = mutableStateOf<Gym?>(null)

    private var apiService: GymsApiService

    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("") /*TODO: ENTER YOUR BASE URL in baseUrl("")*/
            .build()

        apiService = retrofit.create(GymsApiService::class.java)

        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            val gym = getGymFromRemoteDB(id)
            state.value = gym
        }
    }

    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGym(id).values.first().let {
            Gym(
                id = it.id,
                name =  it.name,
                place =  it.place,
                isOpen =  it.isOpen,
            )
        }
    }
}