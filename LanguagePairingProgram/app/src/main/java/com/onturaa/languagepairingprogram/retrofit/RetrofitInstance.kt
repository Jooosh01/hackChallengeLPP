package com.onturaa.languagepairingprogram.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitInstance @Inject constructor() {
    companion object {
        const val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5M" +
                "mQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV" +
                "4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU"

    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://35.245.117.204/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}