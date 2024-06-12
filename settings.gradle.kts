import dev.kikugie.stonecutter.StonecutterSettings

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.4-beta.5"
}

extensions.configure<StonecutterSettings> {
    kotlinController = true
    centralScript = "build.gradle.kts"

    shared {
        versions("1.20.1", "1.20.4", "1.20.6")
    }
    create(rootProject)
}

rootProject.name = "Template"