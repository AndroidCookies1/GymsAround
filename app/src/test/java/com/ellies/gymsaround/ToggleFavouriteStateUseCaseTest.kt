package com.ellies.gymsaround

import com.ellies.gymsaround.gyms.data.GymsRepository
import com.ellies.gymsaround.gyms.domain.GetSortedGymsUseCase
import com.ellies.gymsaround.gyms.domain.ToggleFavouriteStateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleFavouriteStateUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toggleFavouriteState_updatesFavouriteProperty() = scope.runTest {
        //setup
        val gymsRepo = GymsRepository(TestGymsApiService(), TestGymsDao(), dispatcher)
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepo)
        val useCaseUnderTest = ToggleFavouriteStateUseCase(gymsRepo, getSortedGymsUseCase)

        gymsRepo.loadGyms()
        advanceUntilIdle()

        val gyms = DummyGymsList.getDomainDummyGymsList()
        val gymUnderTest = gyms[0]
        val isFav = gymUnderTest.isFavourite

        val updatedGymsList = useCaseUnderTest(gymUnderTest.id, isFav)
        advanceUntilIdle()

        //assertion
        assert(updatedGymsList[0].isFavourite == !isFav)
    }
}