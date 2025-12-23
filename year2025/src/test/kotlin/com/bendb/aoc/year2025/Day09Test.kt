package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    val sampleInput: String
      get() = """
            |7,1
            |11,1
            |11,7
            |9,7
            |9,5
            |2,5
            |2,3
            |7,3
        """.trimMargin()

    val testInput: String
      get() = javaClass.classLoader.getResource("day09/input.txt")!!.readText()

    @Test
    fun testPartOne() {
        assertEquals(4750092396, Day09.partOne(testInput))
    }

    @Test
    fun testPartTwo() {
      // 1_363_080_015
        assertEquals(24, Day09.partTwo(testInput))
    }
}
