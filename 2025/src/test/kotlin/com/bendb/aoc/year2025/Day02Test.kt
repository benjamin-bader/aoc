package com.bendb.aoc.year2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    val testInput: String
        get() {
            return Day01::class.java.classLoader.getResource("day02/input.txt")!!.readText().trim()
        }

    @Test
    fun testPartOne() {
        val input = testInput

        assertEquals(30608905813, Day02.partOne(input))
    }
    
    @Test
    fun testPartTwo() {
        assertEquals(31898925685, Day02.partTwo(testInput))
    }
}