import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    afterEvaluate {
        val isAndroidApplication = plugins.hasPlugin("com.android.application")
        val isAndroidLibrary = plugins.hasPlugin("com.android.library")

        if (isAndroidApplication || isAndroidLibrary) {
            configure<BaseExtension> {
                compileSdkVersion(36)

                defaultConfig {
                    minSdk = 30
                    targetSdk = 36
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    named("release") {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
        }
    }
}