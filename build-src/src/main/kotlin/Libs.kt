package com.skaskasian.buildlogic

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.the

internal val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

object Libs {

    object Versions {

        const val androidxCoreKtx = "1.12.0"
        const val androidxAppCompat = "1.6.1"

        const val agpVersion = "8.2.2"
    }

    object Dependencies {

        const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.androidxCoreKtx}"
        const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    }

    object Plugins {

        val agp = Plugin(
            id = "com.android.application",
            version = Versions.agpVersion
        )

        val androidAppPlugin = Plugin(
            id = "android-app-plugin",
            version = ""
        )
    }
}

data class Plugin(
    val id: String,
    val version: String
)

fun DependencyHandlerScope.applyAndroidxDependencies() {
    implementation(Libs.Dependencies.androidxCoreKtx)
    implementation(Libs.Dependencies.androidxAppCompat)
}

fun DependencyHandlerScope.applyComposeDependencies(project: Project) {
    implementation(platform(project.libs.androidx.compose.bom))
    implementation(project.libs.androidx.compose.animation)
    implementation(project.libs.androidx.compose.runtime)
    implementation(project.libs.androidx.compose.material.iconsExtended)
    implementation(project.libs.androidx.compose.foundation.layout)
    implementation(project.libs.androidx.compose.ui.util)
    implementation(project.libs.androidx.compose.ui.graphics)
    implementation(project.libs.androidx.compose.ui.text)
    implementation(project.libs.androidx.compose.ui.tooling.preview)
    implementation(project.libs.androidx.compose.material3)
    implementation(project.libs.androidx.compose.materialWindow)
}

fun DependencyHandlerScope.applyRoom(project: Project) {
    project.libs.also { libs ->
        implementation(libs.androidx.room)
        implementation(libs.androidx.roomKtx)
        ksp(libs.androidx.roomCompiler)
    }
}