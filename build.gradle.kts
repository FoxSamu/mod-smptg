import net.fabricmc.loom.configuration.ide.RunConfigSettings

plugins {
    id("net.fabricmc.fabric-loom")
    id("maven-publish")

    idea
    eclipse
}

// Some essential information about the build process:
// - We use Loom's source splitting to split client and common sources.
// - We use a separate source set for data generation, since this is a bit broken with split sources.
// - The data generation code is not included in the built jar files since it's purely for development.
// - When running the server or the client, data generation classes are on the classpath.
// - The data generator is ran each time the client or server resources are reloaded, done via a mixin.

// Import properties from gradle.properties
val mod_version: String by project
val mod_group: String by project
val mod_id: String by project

val minecraft_version: String by project
val loader_version: String by project
val fabric_version: String by project
val modmenu_version: String by project
val sodium_version: String by project
val iris_version: String by project

// This is where datagen puts generated resources
val generatedResourcesDir = layout.buildDirectory.get().file("/generated/resources").asFile


// Configuration
// ==================================================================================

version = mod_version
group = mod_group

base {
    archivesName = mod_id
}

// IDE settings: we want sources to be automatically downloaded so we can look through them
idea {
    module {
        isDownloadSources = true
    }
}

eclipse {
    classpath {
        isDownloadSources = true
    }
}

// Java settings
java {
    // Export a sources jar
    withSourcesJar()

    // Use Java 25
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

// Loom settings
loom {
    // Access widener
    // This AW is used for all code, common, client and datagen, since Loom allows
    // only one access widener.
    accessWidenerPath = file("$projectDir/src/main/resources/smptg.accesswidener")

    // Split client and common sources
    splitEnvironmentSourceSets()
}

// Source sets settings
sourceSets {
    // Main and client sources must include generated resources as source directories
    main {
        resources {
            srcDir(generatedResourcesDir)
        }
    }

    named("client") {
        resources {
            srcDir(generatedResourcesDir)
        }
    }

    // Create data source set
    create("data")
}

// Configurations settings
configurations {
    // Include main and client classpath in data generator classpath
    named("dataCompileClasspath") {
        extendsFrom(named("compileClasspath").get())
        extendsFrom(named("clientCompileClasspath").get())
    }
}

// Run configurations
// ==================================================================================

loom {
    runs {
        // We use an in-game data generation process, that runs each time the resources of the
        // server or client are reloaded, and then copies the generated resources into the directory
        // where our processed mod resources are. A bit hacky, but it's much faster and more reliable
        // than having to run the data generator manually each time.
        //
        // This method should be called in each run config to configure the datagen correctly. By default
        // it will use ingame behaviour, but when passing ingame=false, it will run the data generator
        // immediately and terminate.
        fun RunConfigSettings.configDatagen(ingame: Boolean = true) {
            afterEvaluate {
                property("summon.output", "$generatedResourcesDir")
                property("summon.namespace", "$mod_id-data")

                if (ingame) {
                    property("summon.mode", "live")
                    property("summon.copy_to", "${sourceSets.main.get().output.resourcesDir}")
                } else {
                    property("summon.mode", "standalone")
                }

                property("smptg.shadercompat.input", "$projectDir/shadercompat")
            }
        }

        // Default client run
        getByName("client") {
            configDatagen()
        }

        // Default server run
        getByName("server") {
            configDatagen()
        }

        // Run with MC_DEBUG_DONT_SAVE_WORLD, for testing world generation
        // Uses separate run directory
        create("testWorldgen") {
            client()
            configDatagen()

            property("MC_DEBUG_ENABLED")
            property("MC_DEBUG_DONT_SAVE_WORLD")
            runDir("run/test_worldgen")
        }

        // Data generator run
        // This creates a runData task and a dedicated run config for data generation, so
        // that we can run the data generator standalone.
        create("data") {
            name("Data Generation")

            client()
            configDatagen(ingame = false)

            runDir("run/datagen")
        }


        // Create working directories
        // RunConfigSettings seems to have a method for this but it doesn't work.
        // Let's do it manually so that generated IDEA run configs aren't invalid.
        configureEach {
            file("$projectDir/$runDir").mkdirs()
        }
    }
}


// Dependencies
// ==================================================================================

repositories {
    maven { // For modmenu
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }

    maven { // Allows pulling mods from modrinth
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
}

dependencies {
    // Define some methods to avoid the ugly invoking a string syntax
    // Normally gradle would generate these methods but since these configurations were defined after
    // plugins were setup, gradle cannot generate these.
    fun DependencyHandlerScope.clientImplementation(notation: Any) = "clientImplementation"(notation)
    fun DependencyHandlerScope.clientRuntimeOnly(notation: Any) = "clientRuntimeOnly"(notation)
    fun DependencyHandlerScope.dataCompileOnly(notation: Any) = "dataCompileOnly"(notation)

    // Minecraft
    // --------------------------------------------------------------

    minecraft("com.mojang:minecraft:${minecraft_version}")


    // Mod depencencies
    // --------------------------------------------------------------
    // We use the API of these mods.

    // Fabric loader + API, the essentials
    implementation("net.fabricmc:fabric-loader:${loader_version}")
    implementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")

    // Modmenu - altho not required we do compile against it
    clientImplementation("com.terraformersmc:modmenu:${modmenu_version}")


    // Runtime Only
    // --------------------------------------------------------------
    // We test against these mods, but don't depend on them.

    // Sodium
    clientRuntimeOnly("maven.modrinth:sodium:${sodium_version}")

    // Iris
    clientRuntimeOnly("maven.modrinth:iris:${iris_version}")

    // Required by Iris to run
    clientRuntimeOnly("org.antlr:antlr4-runtime:4.13.1")
    clientRuntimeOnly("io.github.douira:glsl-transformer:3.0.0-pre3")
    clientRuntimeOnly("org.anarres:jcpp:1.4.14")


    // Datagen source set
    // --------------------------------------------------------------
    // Link source sets correctly

    // Include main and client sources when compiling data generator
    dataCompileOnly(sourceSets.named("main").get().output)
    dataCompileOnly(sourceSets.named("client").get().output)

    // Include data generator classes when running (main dependencies forward to client dependencies)
    runtimeOnly(sourceSets.named("data").get().output)
}


// Publishing
// ==================================================================================
// TODO setup maven/modrinth publish

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = mod_id
            from(components["java"])
        }
    }

    repositories {
    }
}


// Task configurations
// ==================================================================================

// Setup all java compile tasks
tasks.withType<JavaCompile> {
    // Compile for Java 25
    options.release = 25
}

// Setup all resource processing tasks
tasks.withType<ProcessResources> {
    inputs.property("version", mod_version)
    inputs.property("id", mod_id)

    // Fill in ${version} in fabric.mod.json
    filesMatching("fabric.mod.json") {
        expand(
            "version" to "${inputs.properties["version"]}",
            "id" to "${inputs.properties["id"]}",
        )
    }

    // Exclude datagen cache, we don't need this
    exclude(".cache/**")
}

// Setup all JAR archiving tasks
tasks.withType<Jar> {
    inputs.property("archivesName", project.base.archivesName)

    // Run data generator before packing resources...
    dependsOn("runData")

    // ...and include the generated resources in the JAR...
    from(generatedResourcesDir) {
        exclude(".cache/**") // ...except for the cache files
    }

    // Also include license
    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}
