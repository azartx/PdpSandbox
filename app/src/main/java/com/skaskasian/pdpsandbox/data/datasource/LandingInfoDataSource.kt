package com.skaskasian.pdpsandbox.data.datasource

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.skaskasian.pdpsandbox.App
import com.skaskasian.pdpsandbox.presentation.screens.patterns.utils.PatternsFragmentUtils

interface LandingInfoDataSource {

    fun applyLikeToLanding(landingId: Int)
    fun removeLikeFromLanding(landingId: Int)

    fun isLandingLiked(landingId: Int): Boolean
}

/*
*
* Прокси для LandingInfoDataSource.
*
* Проксирует запрос данных. Если пользователь не авторизован, то отдаёт одни данные, иначе другие.
*
* */
class ProxyLandingInfoDataSource : LandingInfoDataSource {

    private val landingInfoDataSource: LandingInfoDataSource =
        if (PatternsFragmentUtils.getInstance().isUserGuest())
            GuestLandingInfoDataSource() else ContentLandingInfoDataSource()

    override fun applyLikeToLanding(landingId: Int) {
        landingInfoDataSource.applyLikeToLanding(landingId)
    }

    override fun removeLikeFromLanding(landingId: Int) {
        landingInfoDataSource.removeLikeFromLanding(landingId)
    }

    override fun isLandingLiked(landingId: Int): Boolean {
        return landingInfoDataSource.isLandingLiked(landingId)
    }
}

class ContentLandingInfoDataSource : LandingInfoDataSource {

    private val prefs: SharedPreferences by lazy {
        App.app.getSharedPreferences("ContentLandingInfo", MODE_PRIVATE)
    }

    override fun applyLikeToLanding(landingId: Int) {
        prefs.edit {
            putBoolean(landingId.toString(), true)
        }
    }

    override fun removeLikeFromLanding(landingId: Int) {
        prefs.edit {
            putBoolean(landingId.toString(), false)
        }
    }

    override fun isLandingLiked(landingId: Int): Boolean {
        return prefs.getBoolean(landingId.toString(), false)
    }
}

class GuestLandingInfoDataSource : LandingInfoDataSource {

    override fun applyLikeToLanding(landingId: Int) = Unit
    override fun removeLikeFromLanding(landingId: Int) = Unit

    override fun isLandingLiked(landingId: Int) = false
}