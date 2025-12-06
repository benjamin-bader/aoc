package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    val sampleInput: String
      get() = """
          |3-5
          |10-14
          |16-20
          |12-18
          |
          |1
          |5
          |8
          |11
          |17
          |32
      """.trimMargin()

    val testInput: String
      get() = javaClass.classLoader.getResource("day05/input.txt")!!.readText()

    @Test
    fun testPartOne() {
        assertEquals(885, Day05.partOne(testInput))
    }

    @Test
    fun testPartTwo() {
        assertEquals(348115621205535, Day05.partTwo(testInput))
    }
}
