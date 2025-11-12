import net.fabricmc.loom.configuration.ide.RunConfigSettings

plugins {
    id("fabric-loom")
    id("maven-publish")

    idea
    eclipse
}

val mod_version: String by project
val mod_group: String by project
val mod_id: String by project

val minecraft_version: String by project
val loader_version: String by project
val fabric_version: String by project
val modmenu_version: String by project
val sodium_version: String by project
val iris_version: String by project

val generatedResourcesDir = "$projectDir/src/main/generated"

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

sourceSets {
    main {
        resources {
            srcDir(generatedResourcesDir)
        }
    }
}

loom {
    accessWidenerPath = file("$projectDir/src/main/resources/smptg.accesswidener")

    runs {
        create("data") {
            client()
            name("Data Generation")

            property("smptg.datagen", "run_and_stop")
            property("smptg.datagen.output", generatedResourcesDir)
        }

        fun RunConfigSettings.configIngameDatagen() {
            afterEvaluate {
                property("smptg.datagen", "run_on_reload")
                property("smptg.datagen.output", generatedResourcesDir)
                property("smptg.datagen.copy_to", "${tasks.processResources.get().destinationDir}")
            }
        }

        getByName("client") {
            configIngameDatagen()
        }

        getByName("server") {
            configIngameDatagen()
        }

        create("testWorldgen") {
            client()
            configIngameDatagen()

            property("MC_DEBUG_ENABLED")
            property("MC_DEBUG_DONT_SAVE_WORLD")
            runDir("run/test_worldgen")

            makeRunDir()
        }
    }
}

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

    modImplementation("net.fabricmc:fabric-loader:${loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")
    modImplementation("com.terraformersmc:modmenu:${modmenu_version}")
    modImplementation("maven.modrinth:sodium:${sodium_version}")

    modRuntimeOnly("maven.modrinth:iris:${iris_version}")

    // Required by Iris to run
    runtimeOnly("org.antlr:antlr4-runtime:4.13.1")
    runtimeOnly("io.github.douira:glsl-transformer:3.0.0-pre3")
    runtimeOnly("org.anarres:jcpp:1.4.14")
}

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

tasks.withType<JavaCompile> {
    options.release = 21
}

tasks.processResources {
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

tasks.jar {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") {
        rename { "${it}_${inputs.properties["archivesName"]}" }
    }
}
