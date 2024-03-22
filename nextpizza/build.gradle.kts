import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    alias(libs.plugins.shadow)
}

group = "de.rechergg"
version = "$version"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(libs.mongodb.reactive.streams)
    implementation(libs.logback.classic)
    implementation(libs.slf4j.api)
    implementation(libs.databind)
    implementation(libs.annontions)
    implementation(libs.javalin)
    implementation(libs.json)
    implementation(libs.joda.time)
    implementation(libs.bcrypt)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.rechergg.nextpizza.Launcher"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

