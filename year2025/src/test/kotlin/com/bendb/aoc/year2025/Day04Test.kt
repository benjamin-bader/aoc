package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    val sampleInput: String
      get() = """
        |..@@.@@@@.
        |@@@.@.@.@@
        |@@@@@.@.@@
        |@.@@@@..@.
        |@@.@@@@.@@
        |.@@@@@@@.@
        |.@.@.@.@@@
        |@.@@@.@@@@
        |.@@@@@@@@.
        |@.@.@@@.@.
      """.trimMargin()

    val testInput: String
      get() = javaClass.classLoader.getResource("day04/input.txt")!!.readText()

    @Test
    fun testPartOne() {
        assertEquals(1551, Day04.partOne(testInput))
    }

    @Test
    fun testPartTwo() {
        assertEquals(9784, Day04.partTwo(testInput))
    }
}