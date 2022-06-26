package com.alex_podolian.stackexchangetestapp.core.presentation.search

import com.alex_podolian.stackexchangetestapp.KEY_ERROR_TEXT
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
        is SearchIntent.SubmitQuery -> loadSearchResults(intent, state, statePublisher, effectPublisher)
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
        statePublisher: StatePublisher<SearchState>,
        effectPublisher: EffectPublisher<SearchEffect>
    ) {
        withContext(Dispatchers.IO) {
            DefaultFetchUsersCase(UserRepository(ApiHelper(NetworkManager.apiService)))
                .invoke(query = intent.query)
                .onStart { statePublisher(state.copy(isLoading = true)) }
                .catch {
                    val data = HashMap<String, Any?>()
                    data.apply {
                        put(KEY_ERROR_TEXT, it.message)
                    }
                    effectPublisher(SearchEffect.NavigateToErrorScreen(data))
                    statePublisher(state.copy(isLoading = false))
                    it.printStackTrace()
                }
                .collect {
                    statePublisher(state.copy(isLoading = false, users = it.items))
                }
        }
    }
}