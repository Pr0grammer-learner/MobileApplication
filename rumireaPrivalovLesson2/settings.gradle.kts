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

rootProject.name = "ru.mirea.Privalov.Lesson2"
include(":app")
include(":multiactivity")
include(":intentfilter")
include(":toastapp")
include(":notificationapp")
include(":dialog")
include(":activitylifecycle")
