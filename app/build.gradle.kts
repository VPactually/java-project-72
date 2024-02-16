plugins {
    id("java")
    jacoco
    id("checkstyle")
    id("io.freefair.lombok") version "8.4"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.jacocoTestReport {
    reports { xml.required.set(true)
    }
}


tasks.test {
    useJUnitPlatform()
}