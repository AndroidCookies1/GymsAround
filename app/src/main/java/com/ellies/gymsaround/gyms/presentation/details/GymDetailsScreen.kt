package com.ellies.gymsaround.gyms.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ellies.gymsaround.gyms.presentation.gymslist.DefaultIcon
import com.ellies.gymsaround.gyms.presentation.gymslist.GymDetails

@Composable
fun GymDetailsScreen(){
    val viewModel: GymDetailsViewModel = viewModel()

    val item = viewModel.state.value

    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            DefaultIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp),
                contentDescription = "Location Icon"
            )
            GymDetails(
                gym = item,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text(
                text = if (item.isOpen) "Gym is Open" else "Gym is Closed",
                color = if (item.isOpen) Color.Green else Color.Red
            )
        }
    }
}