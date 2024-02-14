package com.skaskasian.pdpsandbox

import android.app.Application
import com.skaskasian.pdpsandbox.data.database.ContentDatabase
import com.skaskasian.pdpsandbox.data.database.dao.ContentDao

class App : Application() {

    companion object {

        lateinit var contentDao: ContentDao
    }

    override fun onCreate() {
        super.onCreate()
        contentDao = ContentDatabase.create(this).contentDao
    }
}