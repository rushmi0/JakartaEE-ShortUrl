plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
}

group = "win.rushmi0.extension"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    compileOnly("jakarta.faces:jakarta.faces-api:4.1.2")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("${project.name}-extension-v$version.jar")
}