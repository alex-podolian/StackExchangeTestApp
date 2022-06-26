package com.alex_podolian.stackexchangetestapp.core.presentation.search

import com.alex_podolian.stackexchangetestapp.business.usecases.DefaultFetchUsersCase
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

class SearchStore(private val scope: CoroutineScope) :
    BaseStore<SearchState, SearchIntent, SearchEffect>(scope, SearchState()) {

    override suspend fun processIntent(
        state: SearchState,
        intent: SearchIntent,
        statePublisher: StatePublisher<SearchState>,
        effectPublisher: EffectPublisher<SearchEffect>
    ): Unit = when (intent) {
        is SearchIntent.ChangeQuery -> changeQuery(intent, state, statePublisher)
        is SearchIntent.SubmitQuery -> loadSearchResults(intent, state, statePublisher)
    }

    private fun changeQuery(
        intent: SearchIntent.ChangeQuery,
        state: SearchState,
        statePublisher: StatePublisher<SearchState>
    ) {
        statePublisher(state.copy(queryInput = intent.query))
    }

    private suspend fun loadSearchResults(
        intent: SearchIntent.SubmitQuery,
        state: SearchState,
        statePublisher: StatePublisher<SearchState>
    ) {
        withContext(Dispatchers.IO) {
            DefaultFetchUsersCase(UserRepository(ApiHelper(NetworkManager.apiService)))
                .invoke(query = intent.query)
                .onStart { statePublisher(state.copy(isLoading = true)) }
                .catch {
                    statePublisher(state.copy(isLoading = false))
                    it.printStackTrace()
                }
                .collect {
                    val users = it.items
                    statePublisher(state.copy(isLoading = false, users = users))
                }
        }
    }
}