import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.android.hilt)
    alias(libs.plugins.google.gms.google.services)
}

// App Properties
val appVersionCode: String by project
val appVersionName: String by project
val pbcLiveApplicationId: String by project

android {
    namespace = pbcLiveApplicationId
    compileSdk = libs.versions.compilerSdkVersion.get().toInt()

    defaultConfig {
        applicationId = pbcLiveApplicationId
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = appVersionCode.toInt()
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".debug"

            matchingFallbacks.add("debug")
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
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

    buildFeatures {
        compose = true
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(libs.versions.jvmVersion.get())
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.navigation.compose)

    // DI Hilt
    implementation(libs.google.android.hilt)
    kapt(libs.google.android.hilt.compiler)
    implementation(libs.google.android.hilt.navigation)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.kv.color.palette)

    implementation(project(":ui-profile"))
    implementation(project(":ui-event"))
    implementation(project(":ui-dashboard"))
    implementation(project(":ui-auth"))
    implementation(project(":ui-splash"))
    implementation(project(":lib-common-ui"))
    implementation(project(":lib-data"))
    implementation(project(":lib-local-datastore"))
    implementation(project(":lib-network"))
    implementation(project(":lib-parent"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}