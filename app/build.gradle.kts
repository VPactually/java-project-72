import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    jacoco
    id("checkstyle")
    application
    id("io.freefair.lombok") version "8.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.javalin:javalin-rendering:6.0.0")
    implementation("io.javalin:javalin-bundle:6.0.0")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("gg.jte:jte:3.1.9")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("com.konghq:unirest-java:3.14.5")
    implementation("org.jsoup:jsoup:1.17.2")
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.jacocoTestReport {
    reports { xml.required.set(true)
    }
}


tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}