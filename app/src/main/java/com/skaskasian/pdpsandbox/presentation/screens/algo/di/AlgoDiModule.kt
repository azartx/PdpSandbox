package com.skaskasian.pdpsandbox.presentation.screens.algo.di

import com.skaskasian.pdpsandbox.di.Dependencies
import com.skaskasian.pdpsandbox.di.Injectable
import com.skaskasian.pdpsandbox.di.get
import com.skaskasian.pdpsandbox.presentation.screens.algo.data.MetropolitanMuseumService
import com.skaskasian.pdpsandbox.presentation.screens.algo.data.MuseumRepository
import retrofit2.Retrofit

fun Injectable.createAlgoScopeDependencies(): Dependencies {
    return hashMapOf(
        MetropolitanMuseumService::class
                to lazy { get<Retrofit>().create(MetropolitanMuseumService::class.java) },
        MuseumRepository::class
                to lazy { MuseumRepository(get<MetropolitanMuseumService>()) }
    )
}