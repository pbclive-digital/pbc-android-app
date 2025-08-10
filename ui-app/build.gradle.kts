plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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

    /*kotlinOptions {
        jvmTarget = libs.versions.jvmVersion.get()
    }*/

    buildFeatures {
        compose = true
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

    //implementation(libs.androidx.appcompat)
    //implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}