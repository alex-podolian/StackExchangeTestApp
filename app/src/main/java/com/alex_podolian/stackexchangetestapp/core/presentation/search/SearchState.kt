package com.alex_podolian.stackexchangetestapp.core.presentation.search

import com.alex_podolian.stackexchangetestapp.data.model.User

data class SearchState(
    val isLoading: Boolean = false,
    val queryInput: String? = null,
    val users: List<User>? = null
)