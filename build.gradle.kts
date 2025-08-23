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

tasks.register<Exec>("setupReleasePatch") {
    group = "versioning"
    description = "Increments the patch version for a new release."

    commandLine("bash", "-c", "./scripts/setup_release.sh patch")
}

tasks.register<Exec>("setupReleaseMinor") {
    group = "versioning"
    description = "Increments the minor version and sets the patch version to 0 for a new release."

    commandLine("bash", "-c", "./scripts/setup_release.sh minor")
}

tasks.register<Exec>("setupReleaseMajor") {
    group = "versioning"
    description = "Increments the major version and sets the minor and patch versions to 0 for a new release."

    commandLine("bash", "-c", "./scripts/setup_release.sh major")
}