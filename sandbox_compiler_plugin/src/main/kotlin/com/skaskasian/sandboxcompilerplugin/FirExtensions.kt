package com.skaskasian.sandboxcompilerplugin

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.expressions.builder.buildFunctionCall
import org.jetbrains.kotlin.fir.extensions.FirExtensionApiInternals
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirFunctionCallRefinementExtension
import org.jetbrains.kotlin.fir.resolve.calls.candidate.CallInfo
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol

@FirExtensionApiInternals
class FirExtensions(
    session: FirSession,
    private val logger: MessageCollector
) : FirFunctionCallRefinementExtension(session) {
    override fun intercept(callInfo: CallInfo, symbol: FirNamedFunctionSymbol): CallReturnType? {
        return null
    }

    override fun transform(call: FirFunctionCall, originalSymbol: FirNamedFunctionSymbol): FirFunctionCall {
        return buildFunctionCall {

        }
    }
}

class PlaygroundFirAdditionalCheckersExtension(
    firSession: FirSession,
    private val logger: MessageCollector
) : FirAdditionalCheckersExtension(firSession) {

    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val classCheckers: Set<FirClassChecker> = setOf(
            PlaygroundClassChecker(logger)
        )
    }
}

@FirExtensionApiInternals
class SandboxFirExtensionRegistrar(private val logger: MessageCollector) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {

        val firExtFactory = FirFunctionCallRefinementExtension.Factory {
            FirExtensions(it, logger)
        }
        val classCheckerFactory = FirAdditionalCheckersExtension.Factory {
            PlaygroundFirAdditionalCheckersExtension(it, logger)
        }


        +firExtFactory
        +classCheckerFactory
    }
}