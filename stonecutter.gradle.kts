plugins {
    id("dev.kikugie.stonecutter")
    id("me.fallenbreath.yamlang") version "1.3.+" apply false
    id("dev.architectury.loom") version "1.4-SNAPSHOT" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.4.+" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}
stonecutter active "1.20.4" /* [SC] DO NOT EDIT */

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}

subprojects.forEach {
    if (path.split(':').size != 3) return@forEach
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")
    apply(plugin = "me.fallenbreath.yamlang")
}
