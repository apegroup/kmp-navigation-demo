rootProject.name = "NavigationDemo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/apegroup/revolver")
            content { includeGroup("com.umain") }

            credentials {
                username = System.getenv("APEGROUPCI_USERNAME") ?: extra.properties["GH_USERNAME"] as? String
                password = System.getenv("APEGROUPCI_TOKEN") ?: extra.properties["GH_TOKEN"] as? String
            }
        }
    }
}

include(":composeApp")
include(":shared")