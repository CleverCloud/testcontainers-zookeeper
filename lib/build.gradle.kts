plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

version = "0.1.0"

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val junitVersion = "5.4.2"
val testContainersVersion = "1.15.3"
val zooKeeperVersion = "3.7.0"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.apache.zookeeper:zookeeper:$zooKeeperVersion")
    testImplementation("org.slf4j:slf4j-simple:1.7.32")

    implementation("org.testcontainers:testcontainers:$testContainersVersion")
}

tasks {
    jar {
        manifest {
            attributes(mapOf("Implementation-Title" to project.name,
                    "Implementation-Version" to project.version))
        }
    }

    test {
        testLogging {
            testLogging {
                events ("passed", "skipped", "failed", "standardOut", "standardError")
                showStackTraces = true
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
    }
}
