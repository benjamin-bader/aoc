package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    val sampleInput: String
      get() = """
          |987654321111111
          |811111111111119
          |234234234234278
          |818181911112111
      """.trimMargin()

    val testInput: String
      get() = javaClass.classLoader.getResource("day03/input.txt")!!.readText()

    @Test
    fun testPartOne() {
        assertEquals(17332, Day03.partOne(testInput))
    }
    
    @Test
    fun testPartTwo() {
        assertEquals(172516781546707, Day03.partTwo(testInput))
    }
}