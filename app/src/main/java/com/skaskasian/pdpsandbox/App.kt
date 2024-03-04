package com.skaskasian.pdpsandbox

import android.app.Application
import com.skaskasian.pdpsandbox.data.database.ContentDatabase
import com.skaskasian.pdpsandbox.data.database.dao.ContentDao
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl.PatternCommandsFactory
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl.PatternsExampleCommandManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.Timestamp
import java.util.Calendar

class App : Application() {

    companion object {

        lateinit var app: App
        lateinit var contentDao: ContentDao
    }

    private val commandsFactory = PatternCommandsFactory()
    private val commandManager by lazy { PatternsExampleCommandManager() }

    override fun onCreate() {
        super.onCreate()
        app = this
        contentDao = ContentDatabase.create(this).contentDao

        if (BuildConfig.IS_COMMANDS_SPAMMING_ENABLED) {
            CoroutineScope(Dispatchers.Default).launch {
                while (true) {
                    delay(1000)
                    commandManager.setCommand(commandsFactory.createLoggingCommand(Calendar.getInstance().time.toString()))
                    delay(1000)
                    commandManager.setCommand(commandsFactory.createToastCommand("Toast command was executed"))
                }
            }
        }
    }
}