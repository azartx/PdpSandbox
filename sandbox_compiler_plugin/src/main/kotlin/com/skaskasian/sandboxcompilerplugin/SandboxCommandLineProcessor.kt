package com.skaskasian.sandboxcompilerplugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

private const val PLUGIN_ID = "sandboxcompiler"

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class SandboxCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = PLUGIN_ID
    override val pluginOptions: Collection<AbstractCliOption> = emptyList()
}