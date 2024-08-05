package org.pastalab.fray.gradle

import org.pastalab.fray.gradle.tasks.PrepareWorkspaceTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

class FrayPlugin : Plugin<Project> {
  override fun apply(target: Project) {

    val extension = target.extensions.create("fray", FrayExtension::class.java)

    target.afterEvaluate {
      val frayVersion = extension.version
      val os = DefaultNativePlatform.getCurrentOperatingSystem().toFamilyName()
      val arch = DefaultNativePlatform.getCurrentArchitecture().name
      val frayJdk = target.dependencies.add("testImplementation", "org.pastalab.fray:jdk:$frayVersion")
      val frayJvmti =
          target.dependencies.add(
              "testImplementation", "org.pastalab.fray:jvmti:$frayVersion:$os-$arch")
      val frayInstrumentation =
          target.dependencies.add(
              "testImplementation", "org.pastalab.fray:instrumentation:$frayVersion")
      val javaPath = "${target.rootProject.layout.buildDirectory.get().asFile}/${Commons.JAVA_PATH}"
      val jvmtiPath =
          "${target.rootProject.layout.buildDirectory.get().asFile}/${Commons.JVMTI_BASE}"
      target.dependencies.add("testImplementation", "org.pastalab.fray:core:$frayVersion")
      target.dependencies.add("testImplementation", "org.pastalab.fray:junit:$frayVersion")
      target.dependencies.add("testCompileOnly", "org.pastalab.fray:runtime:$frayVersion")
      val jlink =
          target.tasks.register("jlink", PrepareWorkspaceTask::class.java).get().apply {
            this.frayJdk.set(frayJdk)
            this.frayJvmti.set(frayJvmti)
          }
      target.tasks.register("frayTest", Test::class.java) {
        it.useJUnitPlatform { it.includeEngines("fray") }
        it.executable(javaPath)
        it.jvmArgs("-agentpath:$jvmtiPath/libjvmti.so")
        it.jvmArgs(
            "-javaagent:${it.project.configurations.detachedConfiguration(frayInstrumentation).resolve().first()}")
        it.jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
        it.jvmArgs("--add-opens", "java.base/java.util.concurrent.atomic=ALL-UNNAMED")
        it.jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")
        it.jvmArgs("--add-opens", "java.base/java.io=ALL-UNNAMED")
        it.jvmArgs("--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED")
        it.jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED")
        it.jvmArgs(
            "-Dfray.workDir=${target.layout.buildDirectory.get().asFile}/${Commons.TEST_WORK_DIR}")
        it.dependsOn(jlink)
      }
    }
  }
}
