package com.alex_podolian.stackexchangetestapp.core.presentation

import com.alex_podolian.stackexchangetestapp.utils.SharedAsyncWrapper
import com.alex_podolian.stackexchangetestapp.utils.StateAsyncWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

typealias StatePublisher<State> = (State) -> Unit
typealias EffectPublisher<Effect> = (Effect) -> Unit

private typealias IntentCollector<Intent> = suspend (Intent) -> Unit

abstract class BaseStore<State, Intent, Effect>(private val scope: CoroutineScope, initialState: State) {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateAsyncWrapper<State>
        get() = StateAsyncWrapper(_state)

    private val _effect =
        MutableSharedFlow<Effect>(onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1)
    val effect: SharedAsyncWrapper<Effect>
        get() = SharedAsyncWrapper(_effect)

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow(extraBufferCapacity = 100)
    private val intent: SharedFlow<Intent>
        get() = _intent

    private val intentCollector: IntentCollector<Intent> = { intent ->
        processIntent(state.value, intent, this::publishState, this::publishEffect)
    }

    init {
        scope.launch { intent.collect { intent -> intentCollector(intent) } }
    }

    fun dispatch(intent: Intent) {
        _intent.tryEmit(intent)
    }

    abstract suspend fun processIntent(
        state: State,
        intent: Intent,
        statePublisher: StatePublisher<State>,
        effectPublisher: EffectPublisher<Effect>
    )

    private fun publishEffect(effect: Effect) {
        _effect.tryEmit(effect)
    }

    private fun publishState(state: State) {
        _state.tryEmit(state)
    }

    fun onDetach() {
        scope.cancel()
    }
}