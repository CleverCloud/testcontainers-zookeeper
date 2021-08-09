plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "com.clever-cloud"
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
val slf4jVersion = "1.7.32"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.apache.zookeeper:zookeeper:$zooKeeperVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("org.testcontainers:testcontainers:$testContainersVersion")
}

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version
                )
            )
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "testcontainers-zookeeper"
            from(components["java"])
            pom {
                name.set("testcontainers-zookeeper")
                description.set("This project provide an Apache ZooKeeper implementation for testcontainers")
                url.set("https://github.com/CleverCloud/testcontainers-zookeeper")
                scm {
                    connection.set("scm:git:https://git@github.com/CleverCloud/testcontainers-zookeeper.git")
                    developerConnection.set("scm:git:git@github.com:CleverCloud/testcontainers-zookeeper.git")
                    url.set("https://github.com/CleverCloud/testcontainers-zookeeper.git")
                }
            }
        }
    }

    nexusPublishing {
        repositories {
            sonatype()
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}