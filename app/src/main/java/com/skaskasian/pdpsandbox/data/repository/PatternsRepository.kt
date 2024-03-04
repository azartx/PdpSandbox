package com.skaskasian.pdpsandbox.data.repository

import com.skaskasian.pdpsandbox.data.datasource.LandingInfoDataSource
import com.skaskasian.pdpsandbox.data.datasource.ProxyLandingInfoDataSource

class PatternsRepository {

    private val landingInfoDataSource: LandingInfoDataSource = ProxyLandingInfoDataSource()

    fun applyLikeToLanding(landingId: Int) {
        landingInfoDataSource.applyLikeToLanding(landingId)
    }

    fun removeLikeFromLanding(landingId: Int) {
        landingInfoDataSource.removeLikeFromLanding(landingId)
    }

    fun isLiked(landingId: Int): Boolean {
        return landingInfoDataSource.isLandingLiked(landingId)
    }
}