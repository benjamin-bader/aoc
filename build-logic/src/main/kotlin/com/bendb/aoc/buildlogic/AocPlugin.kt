package com.bendb.aoc.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.jvm.JvmTestSuite
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.testing.base.TestingExtension

class AocPlugin : Plugin<Project> {
    @Suppress("UnstableApiUsage")
    override fun apply(target: Project) {
        target.plugins.apply("java-library")
        target.plugins.apply("org.jetbrains.kotlin.jvm")

        val groupName = target.findProperty("GROUP")!!.toString()
        val versionName = target.findProperty("VERSION_NAME")!!.toString()

        target.group = groupName
        target.version = versionName

        val catalogsExtension = target.extensions.findByType(VersionCatalogsExtension::class.java)!!
        val libs = catalogsExtension.named("libs")
        val kotlinTest = libs.findLibrary("kotlin-test").get()
        val junitPlatformLauncher = libs.findLibrary("junit-platformLauncher").get()

        target.dependencies.apply {
            add("testImplementation", kotlinTest)
            add("testRuntimeOnly", junitPlatformLauncher)
        }

        target.extensions.configure(TestingExtension::class.java) { ext ->
            ext.suites.named("test") {
                val suite = it as JvmTestSuite

                suite.useJUnitJupiter()
                suite.targets.configureEach { target ->
                    target.testTask.configure { task ->
                        task.testLogging { logging ->
                            logging.events(TestLogEvent.FAILED)
                            logging.exceptionFormat = TestExceptionFormat.FULL
                            logging.showStackTraces = true
                            logging.showExceptions = true
                            logging.showCauses = true
                        }
                    }
                }
            }
        }

        target.tasks.register("newDay", NewDayTask::class.java) { task ->
            task.group = "aoc"
            task.description = "Creates a new DayXX.kt file for the next day."
        }
    }
}
