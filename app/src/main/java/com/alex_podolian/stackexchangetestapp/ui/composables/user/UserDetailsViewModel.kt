package com.alex_podolian.stackexchangetestapp.ui.composables.user

import androidx.lifecycle.viewModelScope
import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsEffect
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsIntent
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsState
import com.alex_podolian.stackexchangetestapp.core.presentation.userdetails.UserDetailsStore
import com.alex_podolian.stackexchangetestapp.ui.composables.BaseViewModel

class UserDetailsViewModel(userId: String) : BaseViewModel<UserDetailsState, UserDetailsIntent, UserDetailsEffect>() {
    override val store: BaseStore<UserDetailsState, UserDetailsIntent, UserDetailsEffect> =
        UserDetailsStore(viewModelScope)

    init {
        dispatch(UserDetailsIntent.FetchTopTags(userId))
    }
}