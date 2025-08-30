import java.net.URI

listOf(
    ":ui-profile",
    ":ui-event",
    ":ui-dashboard",
    ":ui-auth",
    ":ui-splash",
    ":ui-app",
    ":lib-local-datastore",
    ":lib-network",
    ":lib-data",
    ":lib-common-ui",
    ":lib-parent",
).forEach {
    include(it)
}
rootProject.name = "pbc-android-app"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when(requested.id.toString()) {
                in listOf(
                    "com.google.gms.google-services"
                ) -> useModule("com.google.gms:google-services:${requested.version}")
                in listOf(
                    "kotlin-serialization",
                ) -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                in listOf(
                    "kotlin-kapt"
                ) -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.google.dagger.hilt.android" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
                else -> return@eachPlugin
            }
        }
    }

    repositories {
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./gradle/version-catalog/libs.versions.toml"))
        }
    }

    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}
 