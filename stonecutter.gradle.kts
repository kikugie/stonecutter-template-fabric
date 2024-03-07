plugins {
    id("dev.kikugie.stonecutter")
    id("me.fallenbreath.yamlang") version "1.3.+" apply false
    id("fabric-loom") version "1.5-SNAPSHOT" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.4.+" apply false
}
stonecutter active "1.19.4" /* [SC] DO NOT EDIT */

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}
