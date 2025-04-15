package com.skaskasian.sandboxcompilerplugin.firextensions

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionApiInternals
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirFunctionCallRefinementExtension

@FirExtensionApiInternals
class SandboxFirExtensionRegistrar(private val logger: MessageCollector) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {

        val firExtFactory = FirFunctionCallRefinementExtension.Factory {
            FunctionCallExtension(it)
        }
        val classCheckerFactory = FirAdditionalCheckersExtension.Factory {
            AdditionalCheckersExtension(it, logger)
        }

        +firExtFactory
        +classCheckerFactory
    }
}