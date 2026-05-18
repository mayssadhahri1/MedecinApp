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
        maven { url = uri("https://jitpack.io") } // <-- ajouté pour MPAndroidChart
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // <-- changer FAIL_ON_PROJECT_REPOS en PREFER_SETTINGS
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // <-- ajouté pour MPAndroidChart
    }
}

rootProject.name = "MedecinApp"
include(":app")
