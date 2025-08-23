plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.sonar) apply true
}

apply {
    from("$rootDir/quality/sonar/sonar-root.gradle")
    from("$rootDir/quality/jacoco/jacoco-merge.gradle")
}