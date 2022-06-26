package com.alex_podolian.stackexchangetestapp.core.presentation.userdetails

import com.alex_podolian.stackexchangetestapp.business.usecases.DefaultFetchUsersTopTagsCase
import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import com.alex_podolian.stackexchangetestapp.core.presentation.EffectPublisher
import com.alex_podolian.stackexchangetestapp.core.presentation.StatePublisher
import com.alex_podolian.stackexchangetestapp.data.api.ApiHelper
import com.alex_podolian.stackexchangetestapp.data.api.NetworkManager
import com.alex_podolian.stackexchangetestapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class UserDetailsStore(private val scope: CoroutineScope) :
    BaseStore<UserDetailsState, UserDetailsIntent, UserDetailsEffect>(scope, UserDetailsState()) {

    override suspend fun processIntent(
        state: UserDetailsState,
        intent: UserDetailsIntent,
        statePublisher: StatePublisher<UserDetailsState>,
        effectPublisher: EffectPublisher<UserDetailsEffect>
    ): Unit = when (intent) {
        is UserDetailsIntent.FetchTopTags -> fetchTopTags(intent, state, statePublisher)
    }

    private suspend fun fetchTopTags(
        intent: UserDetailsIntent.FetchTopTags,
        state: UserDetailsState,
        statePublisher: StatePublisher<UserDetailsState>
    ) {
        withContext(Dispatchers.IO) {
            DefaultFetchUsersTopTagsCase(UserRepository(ApiHelper(NetworkManager.apiService)))
                .invoke(userId = intent.userId)
                .onStart { statePublisher(state.copy(isLoading = true)) }
                .catch {
                    statePublisher(state.copy(isLoading = false))
                    it.printStackTrace()
                }
                .collect {
                    statePublisher(state.copy(isLoading = false, topTags = it.items))
                }
        }
    }
}