import java.net.URI

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven {
                url =
                    URI("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")
            }
            maven {
                url = URI("https://maven.google.com")
            }
            maven {
                url = URI("https://jitpack.io")
            }
        }
    }
}

rootProject.name = "DoctorApp"
include(":androidApp")
include(":shared")
