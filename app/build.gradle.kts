import com.android.build.api.variant.FilterConfiguration.FilterType
import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.Calendar
import com.skaskasian.buildlogic.applyAndroidxDependencies
import com.skaskasian.buildlogic.applyRoom

plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    alias(libs.plugins.google.ksp)
    id(libs.plugins.pdpsandbox.androidAppPlugin.get().pluginId)
    id("kotlin-parcelize")
}

applyAppName {
    appName = "SomeAppName"
}

android {
    namespace = "com.skaskasian.pdpsandbox"

    defaultConfig {
        applicationId = "com.skaskasian.pdpsandbox"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("boolean", "IS_COMMANDS_SPAMMING_ENABLED", "true")
    }

    flavorDimensions += "app"
    productFlavors {
        create("free") {
            applicationIdSuffix = ".free"
        }
        create("paid") {
            applicationIdSuffix = ".paid"
        }
    }

    splits {
        density {
            isEnable = true
            reset()
            exclude("ldpi", "xxhdpi", "xxxhdpi")
            include("mdpi", "hdpi")
        }
    }

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = "Sandbox_v${versionCode}" +
                        "-${output.getFilter(FilterType.DENSITY.name)}" +
                        "-${name}-${versionName}" +
                        ".${output.outputFile.extension}"
            }
        }
    }
}

dependencies {

    applyAndroidxDependencies()

    applyRoom(project)
    implementation(libs.google.material)
    implementation(libs.paging.common)
    implementation(libs.airbnb.lottie)
    implementation(libs.bumptech.glide)

    kotlinCompilerPluginClasspath(projects.sandboxCompilerPlugin)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

val createFlavourTimestampReportTask: TaskProvider<Task> = tasks.register("createFlavourTimestampReportTask") {
    doLast {
        val currentFlavour = android.productFlavors.firstOrNull { it.isDefault } ?: return@doLast
        val reportFolder =
            File("$rootDir/app/flavoursreport/${currentFlavour.name}", "report.txt")

        if (!reportFolder.parentFile.exists()) reportFolder.parentFile.mkdir()
        if (!reportFolder.exists()) reportFolder.createNewFile()

        reportFolder.writeText("Last usage in millis: ${Calendar.getInstance().time}")
    }
}

val printProjectName: TaskProvider<Task> = tasks.register("printProjectName") {
    println(providers.gradleProperty("projname").get())
}

tasks.build.dependsOn(listOf(createFlavourTimestampReportTask, printProjectName))