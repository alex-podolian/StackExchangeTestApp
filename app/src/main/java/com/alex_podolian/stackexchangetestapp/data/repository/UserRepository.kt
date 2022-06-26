package com.alex_podolian.stackexchangetestapp.data.repository

import com.alex_podolian.stackexchangetestapp.data.api.ApiHelper

class UserRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers(query: String) = apiHelper.getUsers(query)
}