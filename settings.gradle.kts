pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
rootProject.name = "fray-gradle"
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}