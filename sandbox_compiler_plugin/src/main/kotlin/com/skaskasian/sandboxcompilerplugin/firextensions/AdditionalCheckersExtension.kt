package com.skaskasian.sandboxcompilerplugin.firextensions

import com.skaskasian.sandboxcompilerplugin.workers.PlaygroundClassChecker
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

class AdditionalCheckersExtension(
    firSession: FirSession,
    private val logger: MessageCollector
) : FirAdditionalCheckersExtension(firSession) {

    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val classCheckers: Set<FirClassChecker> = setOf(
            PlaygroundClassChecker(logger)
        )
    }
}