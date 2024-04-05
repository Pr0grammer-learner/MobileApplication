pluginManagement {
    repositories {
        google()
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

rootProject.name = "ru.mirea.privalov.Lesson3"
include(":app")
include(":intentapp")
include(":sharer")
include(":favoritebook")
include(":systemintentsapp")
include(":simplefragmentapp")
