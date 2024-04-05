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

rootProject.name = "ru.mirea.Privalov.Lesson4"
include(":app")
include(":thread")
include(":data_thread")
include(":looper")
include(":looper2")
include(":cryptoloader")
include(":serviceapp")
include(":workmanager")
