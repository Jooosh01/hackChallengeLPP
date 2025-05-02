package com.onturaa.languagepairingprogram.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitInstance @Inject constructor() {
    private val okHttpClient = OkHttpClient.Builder()
        .build()

    val apiService: ApiService by lazy{
        Retrofit.Builder()
            .baseUrl("http://35.245.117.204/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}