package com.bendb.aoc.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class NewDayTask @Inject constructor(
    objects: ObjectFactory,
    providers: ProviderFactory,
) : DefaultTask() {
    @Internal
    val groupName: Property<String> =
        objects.property(String::class.java)
            .convention(providers.provider { project.group.toString() })

    private val packageName: String by lazy {
        val name = groupName.get()

        val parts = name.split('.').toMutableList()
        val lastPart = parts[parts.lastIndex]
        if (lastPart.all { it.isDigit() }) {
            parts[parts.lastIndex] = "year$lastPart"
        }

        parts.joinToString(separator = ".") { it.lowercase() }
    }

    @TaskAction
    fun doThatThang() {
        val srcDir = project.layout.projectDirectory.dir("src/main/kotlin")
        val resourcesDir = project.layout.projectDirectory.dir("src/main/resources")
        val testDir = project.layout.projectDirectory.dir("src/test/kotlin")

        val existingDays = srcDir.asFileTree.matching {
            it.include("**/Day*.kt")
        }.mapNotNull {
            val name = it.nameWithoutExtension
            val dayNum = name.removePrefix("Day").toIntOrNull()
            dayNum
        }

        val nextDay = (existingDays.maxOrNull() ?: 0) + 1
        val dayStr = nextDay.toString().padStart(2, '0')

        val classContent = """
            package $packageName

            object Day$dayStr {
              fun partOne(input: String): String {
                TODO()
              }

              fun partTwo(input: String): String {
                TODO()
              }
            }
        """.trimIndent()

        val quotes = "\"\"\""
        val testContent = """
            package $packageName

            import kotlin.test.Test
            import kotlin.test.assertEquals

            class Day${dayStr}Test {
                val sampleInput: String
                  get() = $quotes
                        |
                    $quotes.trimMargin()
            
                val testInput: String
                  get() = javaClass.classLoader.getResource("day$dayStr/input.txt")!!.readText()
            
                @Test
                fun testPartOne() {
                    assertEquals("", Day$dayStr.partOne(sampleInput))
                }
                
                @Test
                fun testPartTwo() {
                    assertEquals("", Day$dayStr.partTwo(sampleInput))
                }
            }
        """.trimIndent()

        val packagePath = packageName.replace('.', File.separatorChar)
        val sourceFile = srcDir.dir(packagePath).file("Day$dayStr.kt").asFile
        val resourceFile = resourcesDir.dir("day$dayStr").file("input.txt").asFile
        val testFile = testDir.dir(packagePath).file("Day${dayStr}Test.kt").asFile

        sourceFile.parentFile.mkdirs()
        resourceFile.parentFile.mkdirs()
        testFile.parentFile.mkdirs()

        sourceFile.writeText(classContent)
        resourceFile.createNewFile()
        testFile.writeText(testContent)
    }
}
