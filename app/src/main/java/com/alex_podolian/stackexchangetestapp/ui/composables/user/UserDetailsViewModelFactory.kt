package com.alex_podolian.stackexchangetestapp.ui.composables.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserDetailsViewModelFactory(
    private val userId: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        UserDetailsViewModel(userId) as T
}