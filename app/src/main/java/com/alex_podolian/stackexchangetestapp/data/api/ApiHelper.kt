package com.alex_podolian.stackexchangetestapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getUsers(query: String) = apiService.getUsers(inName = query)
    suspend fun getTopTags(userId: String) = apiService.getTopTags(id = userId)
}