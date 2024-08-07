plugins {
  kotlin("jvm") version "2.0.0"
  id("java")
  id("java-gradle-plugin")
  id("maven-publish")
  id("com.gradle.plugin-publish") version "1.2.1"
  id("com.ncorti.ktfmt.gradle") version "0.17.0"
}

group = "org.pastalab.fray.gradle"
version = "0.1"

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
}

tasks.test {
  useJUnitPlatform()
}

gradlePlugin {
  website = "https://github.com/cmu-pasta/fray-gradle-plugin"
  vcsUrl = "https://github.com/cmu-pasta/fray-gradle-plugin"
  plugins {
    create("fray") {
      id = "org.pastalab.fray.gradle"
      displayName = "Fray Gradle Plugin"
      implementationClass = "org.pastalab.fray.gradle.FrayPlugin"
      description = "Fray gradle plugin to test concurrency programs."
      tags = listOf("fray", "testing", "concurrency")
    }
  }
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/cmu-pasta/fray")
      credentials {
        username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
        password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
      }
    }
  }
}
