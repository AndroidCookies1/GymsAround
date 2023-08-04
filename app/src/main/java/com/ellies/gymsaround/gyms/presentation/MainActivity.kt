package com.ellies.gymsaround.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ellies.gymsaround.gyms.presentation.details.GymDetailsScreen
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.ellies.gymsaround.gyms.presentation.gymslist.GymsViewModel
import com.ellies.gymsaround.ui.theme.GymsAroundTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymsAroundTheme {
                GymsAroundApp()
            }
        }
    }
}

@Composable
private fun GymsAroundApp() {
   val navController = rememberNavController()

   NavHost(navController = navController, startDestination = "gyms", ) {
       composable(route = "gyms"){
           val vm : GymsViewModel = hiltViewModel()
           GymsScreen(
               state = vm.state.value,
               onItemClick = { id ->
                   navController.navigate("gyms/$id")
               },
               onFavouriteIconClick = { id, oldValue ->
                   vm.toggleFavouriteState(id, oldValue)
               }
           )
       }
       composable(route = "gyms/{gym_id}",
           arguments = listOf(
               navArgument("gym_id") {
                   type = NavType.IntType
               },
           ),
           deepLinks = listOf(
               navDeepLink {
                   uriPattern = "https://www.gymsaround.com/details/{gym_id}"
               }
           )
       ) {
           GymDetailsScreen()
       }
   }

    //fb.com/post/{id}
    //https://www.gymsaround.com/details/7
}
