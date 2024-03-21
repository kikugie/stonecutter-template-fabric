plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.github.johnrengelman.shadow")
}

val subproject = stonecutter.current.project
val mcVersion = stonecutter.current.version
fun prop(name: String) = rootProject.project(stonecutter.current.project).property(name).toString()

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name").toString()
    val version = property("mod.version").toString()
    val group = property("mod.group").toString()
}

val mod = ModData()
val mcDep = property("mod.mc_dep").toString()

architectury {
    platformSetupLoomIde()
    neoForge()
}

val common by configurations.registering
val shadowCommon by configurations.registering
configurations.compileClasspath.get().extendsFrom(common.get())
configurations["developmentNeoForge"].extendsFrom(common.get())

repositories {
    maven("https://maven.neoforged.net/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:${mcVersion}")
    mappings("net.fabricmc:yarn:${mcVersion}+build.${prop("deps.yarn_build")}:v2")
    "neoForge"("net.neoforged:neoforge:${prop("deps.neoforge")}")

    "common"(project(path = ":common:$subproject", configuration = "namedElements")) { isTransitive = false }
    "shadowCommon"(project(path = ":common:$subproject", configuration = "transformProductionNeoForge")) { isTransitive = false }
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        inputs.property("id", mod.id)
        inputs.property("name", mod.name)
        inputs.property("version", mod.version)
        inputs.property("mcdep", mcDep)

        val map = mapOf(
            "id" to mod.id,
            "name" to mod.name,
            "version" to mod.version,
            "mcdep" to mcDep
        )

        filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) { expand(map) }
    }

    shadowJar {
        exclude("fabric.mod.json")
        exclude("architectury.common.json")

        configurations = listOf(shadowCommon.get())
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set(null as String?)

        from(rootProject.file("LICENSE"))
    }

    named<Jar>("sourcesJar") {
        archiveClassifier.set("dev-sources")
        val commonSources = project(":common:$subproject").tasks.named<Jar>("sourcesJar")
        dependsOn(commonSources)
        from(commonSources.get().archiveFile.map { zipTree(it) })
    }

    remapSourcesJar {
        archiveClassifier.set("sources")
    }

    jar {
        archiveClassifier.set("dev")
    }
}

components["java"].run {
    if (this is AdhocComponentWithVariants) {
        withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
            skip()
        }
    }
}