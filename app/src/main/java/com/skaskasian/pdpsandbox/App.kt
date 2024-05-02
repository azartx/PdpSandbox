package com.skaskasian.pdpsandbox

import android.app.Application
import com.skaskasian.pdpsandbox.data.database.ContentDatabase
import com.skaskasian.pdpsandbox.data.database.dao.ContentDao
import com.skaskasian.pdpsandbox.presentation.screens.helloworld.HelloWorldFragment
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.ToastCommandsProcessor

class App : Application() {

    companion object {

        lateinit var app: App
        lateinit var contentDao: ContentDao
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        contentDao = ContentDatabase.create(this).contentDao

        applyCommandsProcessor()
    }

    private fun applyCommandsProcessor() {
        HelloWorldFragment.commandsPublisher.subscribe { isCommandsEnabled ->
            if (isCommandsEnabled) ToastCommandsProcessor.run() else ToastCommandsProcessor.stop()
        }
    }
}