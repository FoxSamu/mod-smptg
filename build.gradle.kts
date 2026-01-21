import net.fabricmc.loom.configuration.ide.RunConfigSettings

plugins {
    id("net.fabricmc.fabric-loom-remap")
    id("maven-publish")

    idea
    eclipse
}

// Some essential information about the buildscript:
// - We use Loom's source splitting to split client and common sources.
// - We use a separate source set for data generation, since this is a bit broken with split sources.
// - The data generation code is not included in the built jar files since it's purely for development.
// - When running the server or the client, data generation classes are on the classpath.

val mod_version: String by project
val mod_group: String by project
val mod_id: String by project

val minecraft_version: String by project
val loader_version: String by project
val fabric_version: String by project
val modmenu_version: String by project
val sodium_version: String by project
val iris_version: String by project

val generatedResourcesDir = layout.buildDirectory.get().file("/generated/resources").asFile


// Configuration
// ==================================================================================

version = mod_version
group = mod_group

base {
    archivesName = mod_id
}

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

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

loom {
    accessWidenerPath = file("$projectDir/src/main/resources/smptg.accesswidener")

    splitEnvironmentSourceSets()
}

sourceSets {
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

    create("data")
}

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
        fun RunConfigSettings.configIngameDatagen() {
            afterEvaluate {
                property("smptg.datagen", "run_on_reload")
                property("smptg.datagen.output", "$generatedResourcesDir")
                property("smptg.datagen.copy_to", "${sourceSets.main.get().output.resourcesDir}")

                property("smptg.shadercompat.input", "$projectDir/shadercompat")
            }
        }

        // Default client run
        getByName("client") {
            configIngameDatagen()
        }

        // Default server run
        getByName("server") {
            configIngameDatagen()
        }

        // Run with MC_DEBUG_DONT_SAVE_WORLD, for testing world generation
        create("testWorldgen") {
            client()
            configIngameDatagen()

            property("MC_DEBUG_ENABLED")
            property("MC_DEBUG_DONT_SAVE_WORLD")
            runDir("run/test_worldgen")
        }

        // Data generator run
        create("data") {
            client()
            name("Data Generation")

            runDir("run/datagen")
            property("smptg.datagen", "run_and_stop")
            property("smptg.datagen.output", "$generatedResourcesDir")
            property("smptg.datagen.copy_to", "${tasks.processResources.get().destinationDir}")

            property("smptg.shadercompat.input", "$projectDir/shadercompat")
        }


        // Create working directories
        configureEach {
            file("$projectDir/$runDir").mkdirs()
        }
    }
}


// Dependencies
// ==================================================================================

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }

    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(loom.officialMojangMappings())

    // Mod depencencies
    // --------------------------------------------------------------

    modImplementation("net.fabricmc:fabric-loader:${loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")

    "modClientImplementation"("com.terraformersmc:modmenu:${modmenu_version}")
    "modClientImplementation"("maven.modrinth:sodium:${sodium_version}")


    // Iris
    // --------------------------------------------------------------

    modRuntimeOnly("maven.modrinth:iris:${iris_version}")

    // Required by Iris to run
    runtimeOnly("org.antlr:antlr4-runtime:4.13.1")
    runtimeOnly("io.github.douira:glsl-transformer:3.0.0-pre3")
    runtimeOnly("org.anarres:jcpp:1.4.14")


    // Datagen source set
    // --------------------------------------------------------------

    // Include main and client sources when compiling data generator
    "dataCompileOnly"(sourceSets.named("main").get().output)
    "dataCompileOnly"(sourceSets.named("client").get().output)

    // Include data generator classes when running (main dependencies forward to client dependencies)
    runtimeOnly(sourceSets.named("data").get().output)
}


// Publishing
// ==================================================================================

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

tasks.withType<JavaCompile> {
    options.release = 21
}

tasks.withType<ProcessResources> {
    inputs.property("version", mod_version)
    inputs.property("id", mod_id)

    filesMatching("fabric.mod.json") {
        expand(
            "version" to "${inputs.properties["version"]}",
            "id" to "${inputs.properties["id"]}",
        )
    }

    exclude(".cache/**")
}

tasks.withType<Jar> {
    inputs.property("archivesName", project.base.archivesName)

    dependsOn("runData")

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}
