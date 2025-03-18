package com.skaskasian.pdpsandbox.presentation.screens.algo.data

import com.skaskasian.pdpsandbox.presentation.screens.algo.data.dto.MuseumObject
import retrofit2.http.GET
import retrofit2.http.Path

interface MetropolitanMuseumService {

    @GET("/public/collection/v1/objects/{id}")
    suspend fun getObject(@Path("id") id: Int): MuseumObject
}