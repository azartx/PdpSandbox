package com.skaskasian.pdpsandbox.presentation.screens.patterns.commands

import com.skaskasian.pdpsandbox.BuildConfig
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl.PatternCommandsFactory
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl.PatternsExampleCommandManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

object ToastCommandsProcessor {

    private val commandsFactory = PatternCommandsFactory()
    private val commandManager by lazy { PatternsExampleCommandManager() }

    private val scope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null

    fun run() {
        if (BuildConfig.IS_COMMANDS_SPAMMING_ENABLED && job == null) {
            job = scope.launch {
                while (true) {
                    delay(1000)
                    commandManager.setCommand(commandsFactory.createLoggingCommand(Calendar.getInstance().time.toString()))
                    delay(1000)
                    commandManager.setCommand(commandsFactory.createToastCommand("Toast command was executed"))
                }
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}