package com.alex_podolian.stackexchangetestapp.core.presentation.userdetails

import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import com.alex_podolian.stackexchangetestapp.core.presentation.EffectPublisher
import com.alex_podolian.stackexchangetestapp.core.presentation.StatePublisher
import kotlinx.coroutines.CoroutineScope

class UserDetailsStore(private val scope: CoroutineScope) :
    BaseStore<UserDetailsState, UserDetailsIntent, UserDetailsEffect>(scope, UserDetailsState()) {

    override suspend fun processIntent(
        state: UserDetailsState,
        intent: UserDetailsIntent,
        statePublisher: StatePublisher<UserDetailsState>,
        effectPublisher: EffectPublisher<UserDetailsEffect>
    ) {
        TODO("Not yet implemented")
    }
}