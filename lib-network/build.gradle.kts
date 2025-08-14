plugins {
    id("java-library")
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    //implementation(libs.google.code.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.loggin.interceptor)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}

java {
    val javaVersion = JavaVersion.toVersion(libs.versions.jvmVersion.get().toInt())
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}