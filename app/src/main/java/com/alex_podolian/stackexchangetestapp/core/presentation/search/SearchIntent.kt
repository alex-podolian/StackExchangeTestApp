package com.alex_podolian.stackexchangetestapp.core.presentation.search

sealed class SearchIntent {
    data class ChangeQuery(val query: String?) : SearchIntent()
    data class SubmitQuery(val query: String) : SearchIntent()
}