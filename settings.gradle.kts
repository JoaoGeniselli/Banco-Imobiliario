pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Toy Bank"
include(":app")
include(":core")
include(":ui")
include(":commons")
include(":test")
include(":features:transaction")
include(":features:newgame")
include(":features:history")
include(":features:home")
include(":features:gameplay")
include(":analytics")
