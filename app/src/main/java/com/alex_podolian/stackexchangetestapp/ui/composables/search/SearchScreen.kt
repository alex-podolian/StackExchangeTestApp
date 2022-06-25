package com.alex_podolian.stackexchangetestapp.ui.composables.search

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchState

@Composable
fun SearchScreen() {
    val viewModel = viewModel<SearchViewModel>()
    val state by viewModel.state.collectAsState(initial = SearchState())

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

    Text(text = "SearchScreen")
}