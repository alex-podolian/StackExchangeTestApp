package com.alex_podolian.stackexchangetestapp.core.presentation.userdetails

sealed class UserDetailsEffect {
    internal data class NavigateToErrorScreen(val data: HashMap<String, Any?>) : UserDetailsEffect()
}