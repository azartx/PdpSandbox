package com.skaskasian.pdpsandbox.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createSharedDependencies(dependencies: Dependencies): Dependencies {
    val gson = createGson()
    return hashMapOf(
        gson::class to lazy { gson },
        Retrofit::class to lazy { createRetrofit(gson) }
    ).apply {
        putAll(dependencies)
    }
}

private fun createGson(): Gson {
    return Gson()
}

private fun createRetrofit(gson: Gson): Retrofit {
    return Retrofit.Builder()
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://collectionapi.metmuseum.org")
        .build()
}

private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}