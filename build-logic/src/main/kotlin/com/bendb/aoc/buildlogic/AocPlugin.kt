package com.bendb.aoc.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.jvm.JvmTestSuite
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Zip
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

        val catalogsExtension = target.extensions.getByType<VersionCatalogsExtension>()
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

        target.tasks.register<NewDayTask>("newDay") { task ->
            task.group = "aoc"
            task.description = "Creates a new DayXX.kt file for the next day."
        }

        val gpgPath = target.providers.exec {
            if (System.getProperty("os.name").startsWith("Windows")) {
                it.commandLine("where.exe", "gpg")
            } else {
                it.commandLine("which", "gpg")
            }
        }.standardOutput.asText.map { it.trim() }

        val sourceSets = target.extensions.getByType(SourceSetContainer::class.java)
        val mainSourceSet = sourceSets.named("main")
        val resources = mainSourceSet.map { it.resources }

        val encryptedInputsFile = target.layout.projectDirectory.file("inputs.zip.gpg")
        val passphraseProvider = target.providers.environmentVariable("AOC_INPUT_PASSPHRASE")
            .orElse(target.providers.gradleProperty("aoc_input_passphrase"))

        val packageResources = target.tasks.register<Zip>("packageResources") { t ->
            t.archiveBaseName.set("inputs")
            t.archiveVersion.set("")
            t.destinationDirectory.set(target.layout.buildDirectory.dir("inputs"))
            t.from(resources)
        }

        val encryptResources = target.tasks.register<GpgTask>("encryptResources") { t ->
            t.executable = gpgPath.get()

            t.command.set(GpgTask.Command.ENCRYPT)
            t.inputFile.set(packageResources.flatMap { it.archiveFile })
            t.outputFile.set(encryptedInputsFile)
            t.passphrase.set(passphraseProvider)
        }

        val decryptResources = target.tasks.register<GpgTask>("decryptResources") { t ->
            t.executable = gpgPath.get()

            t.command.set(GpgTask.Command.DECRYPT)
            t.inputFile.set(encryptedInputsFile)
            t.outputFile.set(target.layout.buildDirectory.dir("restored-inputs").map { it.file("inputs.zip") })
            t.passphrase.set(passphraseProvider)
        }

        val restoreResources = target.tasks.register<Copy>("restoreResources") { t ->
            t.onlyIf {
                encryptedInputsFile.asFile.exists()
            }

            val zipFile = decryptResources.flatMap { it.outputFile }

            t.from(target.zipTree(zipFile))

            t.into(resources.map { it.srcDirs.first() })
        }
    }
}
