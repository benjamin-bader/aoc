package com.bendb.aoc.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.time.LocalDateTime

abstract class NewYearTask : DefaultTask() {
    @get:Input
    abstract val year: Property<Int>

    @TaskAction
    fun setUpProject() {
        val slug = "year${year.get()}"
        val projectDir = project.layout.projectDirectory.dir(slug)
        val buildFile = projectDir.file("build.gradle")
        val propsFile = projectDir.file("gradle.properties")

        projectDir.asFile.mkdirs()

        buildFile.asFile.writeText("""
            |plugins {
            |  id 'aoc'
            |}
            |
            |group = GROUP
            |version = VERSION_NAME
            |
        """.trimMargin())

        propsFile.asFile.writeText("""
            |GROUP=com.bendb.aoc.$slug
            |
        """.trimMargin())
    }
}
