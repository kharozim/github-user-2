package com.example.githubuser2.repository.remote

import com.example.githubuser2.BuildConfig
import com.example.githubuser2.repository.remote.services.UserService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.github.com"
    private const val TOKEN = "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"

    private fun getClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor { chain ->
            val origin = chain.request()
            val request = origin.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", TOKEN)
                .method(origin.method, origin.body)
                .build()
            chain.proceed(request)
        }
        if (!BuildConfig.IS_RELEASE) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient.addInterceptor(interceptor)
        }
        return okHttpClient.build()
    }


   private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(getClient())
        .build()

    val userService: UserService = retrofit.create(UserService::class.java)

}