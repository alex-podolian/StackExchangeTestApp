package com.alex_podolian.stackexchangetestapp.core.presentation.search

sealed class SearchEffect {
    internal data class NavigateToErrorScreen(val data: HashMap<String, Any?>) : SearchEffect()
}