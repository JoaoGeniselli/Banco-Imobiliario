import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kover) apply true
    alias(libs.plugins.sonar) apply true
}

apply { from("$rootDir/quality/kover/kover.gradle") }
apply { from("$rootDir/quality/sonar/sonar-root.gradle") }

dependencies {
    kover(project(":app"))
    kover(project(":features:newgame"))
    kover(project(":features:history"))
    kover(project(":features:transaction"))
    kover(project(":features:gameplay"))
    kover(project(":features:home"))
    kover(project(":core"))
    kover(project(":commons"))
    kover(project(":ui"))
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
                        isMinifyEnabled = isAndroidApplication
                        isShrinkResources = isAndroidApplication
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "$rootDir/quality/r8/default-obfuscation.pro",
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