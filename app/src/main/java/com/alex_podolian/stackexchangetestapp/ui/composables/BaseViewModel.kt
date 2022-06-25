package com.alex_podolian.stackexchangetestapp.ui.composables

import androidx.lifecycle.ViewModel
import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Intent, Effect> : ViewModel() {
    protected abstract val store: BaseStore<State, Intent, Effect>

    val state: StateFlow<State>
        get() = store.state.sync

    val effect: SharedFlow<Effect>
        get() = store.effect.sync

    fun dispatch(intent: Intent) {
        store.dispatch(intent)
    }
}