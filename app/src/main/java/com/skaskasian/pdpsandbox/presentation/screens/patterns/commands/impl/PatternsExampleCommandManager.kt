package com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.impl

import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.api.Command
import com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.api.CommandsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.SynchronousQueue

class PatternsExampleCommandManager : CommandsManager {

    private val commandsQueue: SynchronousQueue<Command> = SynchronousQueue()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(2000)
                commandsQueue.poll()
                    ?.let(::executeCommand)
            }
        }
    }

    override fun setCommand(command: Command) {
        commandsQueue.put(command)
    }

    override fun executeCommand(command: Command) {
        command.execute()
    }
}