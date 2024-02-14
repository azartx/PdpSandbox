package com.skaskasian.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class AndroidAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        applyAndroidSettings(project)
        applyAndroidAppNameTask(project)
    }

    private fun applyAndroidAppNameTask(project: Project) {
        val applyNameExtension = applyApplicationNameExtension(project)

        project.afterEvaluate {
            project.tasks.register<AppNameTask>("applyAppName") {
                dependsOn(":app:preBuild")
                appName = applyNameExtension.appName
            }
            // should be work only when executing build task in :app module
            // or executing :applyAppName task
            tasks
                .getByPath(":app:preBuild")
                .finalizedBy("applyAppName")
        }
    }

    private fun applyApplicationNameExtension(project: Project): ApplyNameExtension {
        return project.extensions.create(
            ApplyNameExtension::class.java,
            "applyAppName",
            ApplyNameExtensionImpl::class.java
        )
    }

    private fun applyAndroidSettings(project: Project) {
        project.extensions
            .getByType(CommonExtension::class.java)
            .apply {
                defaultConfig {
                    compileSdk = 34
                    minSdk = 25

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                buildTypes {
                    getByName("release") {
                        isShrinkResources = true
                        isMinifyEnabled = true
                    }
                    getByName("debug") {
                        isShrinkResources = false
                        isMinifyEnabled = false
                    }
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                buildFeatures {
                    viewBinding = true
                }
            }
    }
}