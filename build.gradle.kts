buildscript {

    repositories {
        maven {

            url =
                java.net.URI("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")
        }
        google()
        mavenCentral()
        maven { url = java.net.URI("https://www.jitpack.io") }
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")

    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.0").apply(false)
    id("com.android.library").version("7.3.0").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
