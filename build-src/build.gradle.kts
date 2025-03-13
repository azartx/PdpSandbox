plugins {
    `kotlin-dsl`
}

group = "com.skaskasian.buildlogic"

gradlePlugin {
    plugins {
        create("android-app-plugin") {
            id = "android-app-plugin"
            implementationClass = "com.skaskasian.buildlogic.AndroidAppPlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(gradleKotlinDsl())

    // to apply version catalog under src
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}