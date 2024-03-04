package com.skaskasian.pdpsandbox.presentation.screens.patterns.commands.api

interface CommandsManager {

    fun setCommand(command: Command)

    fun executeCommand(command: Command)
}