package com.ellies.gymsaround

import com.ellies.gymsaround.gyms.data.GymsRepository
import com.ellies.gymsaround.gyms.data.local.GymsDao
import com.ellies.gymsaround.gyms.data.local.LocalGym
import com.ellies.gymsaround.gyms.data.local.LocalGymFavouriteState
import com.ellies.gymsaround.gyms.data.remote.GymsApiService
import com.ellies.gymsaround.gyms.data.remote.RemoteGym
import com.ellies.gymsaround.gyms.domain.GetInitialGymsUseCase
import com.ellies.gymsaround.gyms.domain.GetSortedGymsUseCase
import com.ellies.gymsaround.gyms.domain.ToggleFavouriteStateUseCase
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsScreenState
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun loadingState_isSetCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        val state = viewModel.state.value
        assert(
            state == GymsScreenState(
                gyms = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun loadedContentState_isSetCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        advanceUntilIdle()
        val state = viewModel.state.value
        assert(
            state == GymsScreenState(
                gyms = DummyGymsList.getDomainDummyGymsList(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getViewModel(): GymsViewModel {
        val gymsRepository = GymsRepository(TestGymsApiService(), TestGymsDao(), dispatcher)
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepository)
        val getInitialGymsUseCase = GetInitialGymsUseCase(gymsRepository,getSortedGymsUseCase)
        val toggleFavouriteStateUseCase = ToggleFavouriteStateUseCase(gymsRepository,getSortedGymsUseCase)
        return GymsViewModel(getInitialGymsUseCase, toggleFavouriteStateUseCase, dispatcher)
    }

}


class TestGymsApiService: GymsApiService {
    override suspend fun getGyms(): List<RemoteGym> {
        return DummyGymsList.getDummyGymsList()
    }

    override suspend fun getGym(id: Int): Map<String, RemoteGym> {
        TODO("Not yet implemented")
    }

}

class TestGymsDao: GymsDao {
    private var gyms = HashMap<Int, LocalGym>()

    override suspend fun getAll(): List<LocalGym> {
        return gyms.values.toList()
    }

    override suspend fun addAll(gyms: List<LocalGym>) {
        gyms.forEach {
            this.gyms[it.id] = it
        }
    }

    override suspend fun update(localGymFavouriteState: LocalGymFavouriteState) {
        updateGym(localGymFavouriteState)
    }

    override suspend fun getFavouriteGyms(): List<LocalGym> {
        return gyms.values.toList().filter { it.isFavourite }
    }

    override suspend fun updateAll(gymsStates: List<LocalGymFavouriteState>) {
        gymsStates.forEach {
            updateGym(it)
        }
    }

    private fun updateGym(gymState: LocalGymFavouriteState) {
        val gym = this.gyms[gymState.id]
        gym?.let {
            this.gyms[gymState.id] = gym.copy(isFavourite = gymState.isFavourite)
        }
    }

}
