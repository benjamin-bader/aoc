package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    val sampleInput: String
      get() = """
            |123 328  51 64 
            | 45 64  387 23 
            |  6 98  215 314
            |*   +   *   +  
        """.trimMargin()

    val testInput: String
      get() = javaClass.classLoader.getResource("day06/input.txt")!!.readText()

    @Test
    fun testPartOne() {
        assertEquals(4951502530386, Day06.partOne(testInput))
    }
    
    @Test
    fun testPartTwo() {
        assertEquals(8486156119946, Day06.partTwo(testInput))
    }
}