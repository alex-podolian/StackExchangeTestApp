package com.alex_podolian.stackexchangetestapp.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.stackexchange.com/2.3/"

object NetworkManager {

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getRequestInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build()
    }

    private fun getRequestInterceptor() : Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl: HttpUrl = originalRequest.url
            val url = originalHttpUrl.newBuilder().build()
            val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}