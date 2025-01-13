plugins {
    kotlin("jvm")
    alias(libs.plugins.google.ksp)
}

dependencies {
    compileOnly(kotlin("compiler-embeddable"))

    compileOnly(libs.autoService.annotations)
    ksp(libs.autoService.ksp)
}