package com.skaskasian.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

internal val Project.catalog: VersionCatalog
    get() {
        return extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")
    }

internal fun VersionCatalog.getVersion(name: String): String {
    return try {
        findLibrary(name).get().get().toString()
    } catch (e: Exception) {
        throw Exception("Error getting dependency $name.", e)
    }
}

internal fun DependencyHandlerScope.implementation(dependency: String) {
    "implementation"(dependency)
}

internal fun DependencyHandlerScope.implementation(dependency: Provider<MinimalExternalModuleDependency>) {
    "implementation"(dependency)
}

internal fun DependencyHandlerScope.ksp(dependency: Provider<MinimalExternalModuleDependency>) {
    "ksp"(dependency)
}