package com.skaskasian.sandboxcompilerplugin.utils

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector

fun MessageCollector.warn(message: String) {
    report(CompilerMessageSeverity.WARNING, message)
}