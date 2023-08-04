package com.ellies.gymsaround.gyms.presentation.gymslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ellies.gymsaround.gyms.domain.Gym
import com.ellies.gymsaround.gyms.presentation.SemanticsDescription
import com.ellies.gymsaround.ui.theme.Purple200

@Composable
fun GymsScreen(
    state: GymsScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteIconClick: (id: Int, oldValue: Boolean) -> Unit
){
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(){
            items(state.gyms){ gym ->
                GymItem(
                    gym = gym,
                    onFavouriteIconClick = { id, oldValue -> onFavouriteIconClick(id, oldValue) },
                    onItemClick = { id -> onItemClick(id) }
                )
            }
        }
        if (state.isLoading) CircularProgressIndicator(
            Modifier.semantics {
                this.contentDescription = SemanticsDescription.GYMS_LIST_LOADING
            }
        )
        state.error?.let { Text(it) }
    }

}

@Composable
fun GymItem(gym: Gym, onFavouriteIconClick: (Int, Boolean) -> Unit, onItemClick: (Int) -> Unit) {
    val icon = if (gym.isFavourite){
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(gym.id) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            DefaultIcon(Icons.Filled.Place, Modifier.weight(0.15f), "Location Icon")
            GymDetails(gym, Modifier.weight(0.70f))
            DefaultIcon(icon, Modifier.weight(0.15f), "Favourite Gym Icon"){
                onFavouriteIconClick(gym.id, gym.isFavourite)
            }
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {},
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier, horizontalAlignment: Alignment.Horizontal = Alignment.Start) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.h6,
            color = Purple200
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = gym.place,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
/*

@Preview(showBackground = true)
@Composable
fun _GymScreenPreview(){
    GymsAroundTheme {
        GymsScreen()
    }
}
*/


// Online
/**
 *
 * 1- get data from firebase
 * 2- save data from firebase to local storage/ room database
 * 3- display data from firebase
 * */
// Offline
/**
 * 1- get data from offline from room database
 * 2- display data received from room database
 * */


