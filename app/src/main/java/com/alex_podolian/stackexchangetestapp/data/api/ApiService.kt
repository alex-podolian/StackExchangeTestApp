package com.alex_podolian.stackexchangetestapp.data.api

import com.alex_podolian.stackexchangetestapp.data.model.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("order") order: String = "asc",
        @Query("sort") sort: String = "name",
        @Query("site") site: String = "stackoverflow",
        @Query("pagesize") pageSize: Int = 20,
        @Query("inname") inName: String
    ): Users
}