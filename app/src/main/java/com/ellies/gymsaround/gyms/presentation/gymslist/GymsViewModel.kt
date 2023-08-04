package com.ellies.gymsaround.gyms.presentation.gymslist

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ellies.gymsaround.gyms.data.di.MainDispatcher
import com.ellies.gymsaround.gyms.domain.GetInitialGymsUseCase
import com.ellies.gymsaround.gyms.domain.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavouriteStateUseCase: ToggleFavouriteStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _state by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true,
        )
    )
    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler + dispatcher) {
            val receivedGyms = getInitialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false,
            )
        }
    }

    fun toggleFavouriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler + dispatcher) {
            val updatedGymsList = toggleFavouriteStateUseCase(gymId, oldValue)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }

}