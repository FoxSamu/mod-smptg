pluginManagement {
    val loom_version: String by settings

    plugins {
        id("net.fabricmc.fabric-loom") version loom_version
    }

	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		mavenCentral()
		gradlePluginPortal()
	}
}