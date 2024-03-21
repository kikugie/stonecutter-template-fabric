plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("me.fallenbreath.yamlang")
}

val mcVersion = stonecutter.current.version
fun prop(name: String) = rootProject.project(stonecutter.current.project).property(name).toString()

architectury {
    common(listOf("fabric", "neoforge"))
}

dependencies {
    minecraft("com.mojang:minecraft:${mcVersion}")
    mappings("net.fabricmc:yarn:${mcVersion}+build.${prop("deps.yarn_build")}:v2")
}

java {
    withSourcesJar()
}

yamlang {
    targetSourceSets.set(mutableListOf(sourceSets["main"]))
    inputDir.set("assets/${property("mod.id")}/lang")
}

tasks {
    remapJar {
        archiveClassifier.set(null as String?)

        from(rootProject.file("LICENSE"))
    }
}