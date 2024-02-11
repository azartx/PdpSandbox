plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

gradlePlugin {
    plugins {
        create("android-app-plugin") {
            id = "android-app-plugin"
            implementationClass = "AndroidAppPlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(gradleKotlinDsl())
}