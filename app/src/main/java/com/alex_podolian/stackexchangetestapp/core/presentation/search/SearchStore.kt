package com.alex_podolian.stackexchangetestapp.core.presentation.search

import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import com.alex_podolian.stackexchangetestapp.core.presentation.EffectPublisher
import com.alex_podolian.stackexchangetestapp.core.presentation.StatePublisher
import kotlinx.coroutines.CoroutineScope

class SearchStore(private val scope: CoroutineScope) : BaseStore<SearchState, SearchIntent, SearchEffect>(scope, SearchState()) {

    override suspend fun processIntent(
        state: SearchState,
        intent: SearchIntent,
        statePublisher: StatePublisher<SearchState>,
        effectPublisher: EffectPublisher<SearchEffect>
    ) {
        TODO("Not yet implemented")
    }

}