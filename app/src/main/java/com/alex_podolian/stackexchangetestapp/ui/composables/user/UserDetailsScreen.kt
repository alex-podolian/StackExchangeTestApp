package com.alex_podolian.stackexchangetestapp.ui.composables.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsState

@Composable
fun UserDetailsScreen() {
    val viewModel = viewModel<UserDetailsViewModel>()
    val state by viewModel.state.collectAsState(initial = UserDetailsState())

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                //TODO: implement effect handling
            }
        }
    }

    if (state.isLoading) {
        //TODO: implement loading indicator
    }

    Text(text = "UserDetailsScreen")
}