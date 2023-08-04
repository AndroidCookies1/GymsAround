package com.ellies.gymsaround

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ellies.gymsaround.DummyGymsList.getDummyGymsList
import com.ellies.gymsaround.gyms.presentation.SemanticsDescription
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsScreenState
import com.ellies.gymsaround.ui.theme.GymsAroundTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = emptyList(),
                    isLoading = true,
                ), onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertIsDisplayed()
    }

    @Test
    fun loadedContentState_isActive() {
        val gymsList = getDummyGymsList()

        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = gymsList,
                    isLoading = false,
                ), onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }

        testRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }

    @Test
    fun errorState_isActive() {
        val errorText = "Failed to load data"

        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = emptyList(),
                    isLoading = false,
                    error = errorText
                ), onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onNodeWithText(errorText).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }

    @Test
    fun onItemClick_idIsPassedCorrectly(){
        val gymsList = getDummyGymsList()
        val gymItem = gymsList[0]

        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(state = GymsScreenState(
                    gyms = gymsList,
                    isLoading = false,
                ), onItemClick = { id ->
                    assert( id == gymItem.id)
                }, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        testRule.onNodeWithText(gymItem.name).performClick()
    }

}