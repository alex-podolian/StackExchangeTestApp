package com.alex_podolian.stackexchangetestapp.core.presentation.userdetails

sealed class UserDetailsIntent {
    data class FetchTopTags(val userId: String) : UserDetailsIntent()
}