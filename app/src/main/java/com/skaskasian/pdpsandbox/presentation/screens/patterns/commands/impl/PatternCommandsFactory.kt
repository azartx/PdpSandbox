package com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl

import android.util.Log
import android.widget.Toast
import com.skaskasian.pdpsandbox.App
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.api.Command
import java.util.logging.Handler

class PatternCommandsFactory {

    fun createLoggingCommand(logMessage: String): Command {
        return object : Command {
            override fun execute() {
                Log.e("LoggingCommand", logMessage)
            }
        }
    }

    fun createToastCommand(toastMessage: String): Command {
        return object : Command {
            override fun execute() {
                android.os.Handler(App.app.mainLooper).post {
                    Toast.makeText(App.app, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}