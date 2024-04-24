plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.oneeyedmen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(platform("org.http4k:http4k-bom:5.16.2.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-client-apache")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}