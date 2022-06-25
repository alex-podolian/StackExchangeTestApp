package com.alex_podolian.stackexchangetestapp.ui.composables.search

import androidx.lifecycle.viewModelScope
import com.alex_podolian.stackexchangetestapp.ui.composables.BaseViewModel
import com.alex_podolian.stackexchangetestapp.core.presentation.BaseStore
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchEffect
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchIntent
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchState
import com.alex_podolian.stackexchangetestapp.core.presentation.search.SearchStore

class SearchViewModel : BaseViewModel<SearchState, SearchIntent, SearchEffect>() {
    override val store: BaseStore<SearchState, SearchIntent, SearchEffect> = SearchStore(viewModelScope)
}