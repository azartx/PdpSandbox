import com.android.build.api.variant.FilterConfiguration.FilterType
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.incremental.createDirectory
import java.util.Calendar

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id(Libs.Plugins.androidAppPlugin.id)
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
    tasks.build.dependsOn("createFlavourTimestampReportTask")
}

dependencies {

    applyAndroidxDependencies()
    implementation(libs.google.material)
    implementation(libs.paging.common)
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

task("createFlavourTimestampReportTask") {
    doLast {
        val currentFlavour = android.productFlavors.firstOrNull { it.isDefault } ?: return@doLast
        val reportFolder =
            File("$rootDir/app/flavoursreport/${currentFlavour.name}", "report.txt")

        if (!reportFolder.parentFile.exists()) reportFolder.parentFile.createDirectory()
        if (!reportFolder.exists()) reportFolder.createNewFile()

        reportFolder.writeText("Last usage in millis: ${Calendar.getInstance().time}")
    }
}