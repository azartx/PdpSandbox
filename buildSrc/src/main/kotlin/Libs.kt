import org.gradle.kotlin.dsl.DependencyHandlerScope

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
    "implementation"(Libs.Dependencies.androidxCoreKtx)
    "implementation"(Libs.Dependencies.androidxAppCompat)
}