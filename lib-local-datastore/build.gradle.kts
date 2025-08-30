import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kavi.pbc.droid.lib.datastore"
    compileSdk = libs.versions.compilerSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        val javaVersion = JavaVersion.toVersion(libs.versions.jvmVersion.get().toInt())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(libs.versions.jvmVersion.get())
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Datastore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preference)

    implementation(project(":lib-data"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}