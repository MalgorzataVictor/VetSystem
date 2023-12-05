
plugins {
    kotlin("jvm") version "1.9.0"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.10"
    // Code coverage tool
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    // plugin for ear
    ear
    application
}

group = "me.20102772"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // dependencies for logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")

    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")

    // for Gmail API
    implementation("com.google.api-client:google-api-client:2.2.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.20.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-gmail:v1-rev20220404-2.0.0")
    implementation("com.sun.mail:javax.mail:1.6.2")

    // for Mordant Interface
    implementation("com.github.ajalt.mordant:mordant:2.2.0")

    // dependencies for logging
    earlib("io.github.microutils:kotlin-logging:3.0.5")
    earlib("org.slf4j:slf4j-simple:2.0.9")

    // For Streaming to XML and JSON
    earlib("com.thoughtworks.xstream:xstream:1.4.18")
    earlib("org.codehaus.jettison:jettison:1.4.1")

    // For generating a Dokka Site from KDoc
    earlib("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")

    // for Gmail API
    earlib("com.google.api-client:google-api-client:2.2.0")
    earlib("com.google.auth:google-auth-library-oauth2-http:1.20.0")
    earlib("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    earlib("com.google.apis:google-api-services-gmail:v1-rev20220404-2.0.0")
    earlib("com.sun.mail:javax.mail:1.6.2")

    // for Mordant Interface
    earlib("com.github.ajalt.mordant:mordant:2.2.0")
}

tasks.test {
    useJUnitPlatform()
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
    // for building a fat jar - include all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.ear {
    libDirName = "APP-INF/lib" // put dependent libraries into APP-INF/lib inside the generated EAR
    deploymentDescriptor { // custom entries for application.xml:
        applicationName = "Vet System"
        initializeInOrder = true
        displayName = "VetSystemEar" // defaults to project.name
        description = "My customized EAR for the Gradle documentation"
    }
    manifest.attributes["Main-Class"] = "MainKt"
}
