package com.alex_podolian.stackexchangetestapp.core.presentation.userdetails

import com.alex_podolian.stackexchangetestapp.data.model.TopTag

data class UserDetailsState(
    val isLoading: Boolean = false,
    val topTags: List<TopTag>? = null
)