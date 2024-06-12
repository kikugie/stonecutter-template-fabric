plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.6-SNAPSHOT" apply false
    //id("dev.kikugie.j52j") version "1.0" apply false // Enables asset processing by writing json5 files
    //id("me.modmuss50.mod-publish-plugin") version "0.5.+" apply false // Publishes builds to hosting websites
}
stonecutter active "1.20.1" /* [SC] DO NOT EDIT */

// Builds every version into `build/libs/{mod.version}/`
stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

/*
// Publishes every version
stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}
*/

stonecutter configureEach {
    /*
    See src/main/java/com/example/TemplateMod.java
    and https://stonecutter.kikugie.dev/
    */
    // Swaps replace the scope with a predefined value
    swap("mod_version", "\"${project.property("mod.version")}\";")
    // Constants add variables available in conditions
    const("release", project.property("mod.id") != "template")
    // Dependencies add targets to check versions against
    dependency("fapi", project.property("deps.fabric_api").toString())
}
