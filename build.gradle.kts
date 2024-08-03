plugins {
  kotlin("jvm") version "2.0.0"
  id("java")
  id("java-gradle-plugin")
  id("maven-publish")
  id("com.gradle.plugin-publish") version "1.2.1"
  id("com.ncorti.ktfmt.gradle") version "0.17.0"
}

group = "cmu.pasta.fray.gradle"
version = "1.0"

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
      id = "cmu.pasta.fray.gradle"
      displayName = "Fray Gradle Plugin"
      implementationClass = "cmu.pasta.fray.gradle.FrayPlugin"
      description = "Fray Gradle Plugin"
      tags = listOf("fray", "testing", "concurrency")
    }
  }
}
