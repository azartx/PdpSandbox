package com.skaskasian.sandboxcompilerplugin

import com.skaskasian.sandboxcompilerplugin.utils.warn
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry

class PlaygroundClassChecker(
    private val logger: MessageCollector
) : FirClassChecker(MppCheckerKind.Common) {

    override fun check(
        declaration: FirClass,
        context: CheckerContext,
        reporter: DiagnosticReporter
    ) {

        if (declaration.classKind == ClassKind.ENUM_CLASS) {
            declaration.declarations
                .filterIsInstance<FirEnumEntry>()
                .forEach {
                    logger.warn(it.name.asString())
                }
        }
    }
}