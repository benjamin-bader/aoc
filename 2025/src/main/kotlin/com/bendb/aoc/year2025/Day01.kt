package com.bendb.aoc.year2025

import kotlin.math.absoluteValue

object Day01 {
  fun partOne(): Int {
    val resourceUrl = Day01::class.java.classLoader.getResource("day01/part1.input")
    val input = resourceUrl?.readText() ?: ""
    val lines = input.lines()

    var numberOfTimesAtZero = 0
    var dial = 50
    for (line in lines) {
      if (line.isBlank()) continue

      val dir = if (line.startsWith('L')) -1 else 1
      val numberOfSteps = line.substring(1).toInt()

      dial = (dial + dir * numberOfSteps).mod(100).absoluteValue
      if (dial == 0) {
        numberOfTimesAtZero++
      }
    }

    return numberOfTimesAtZero
  }

  fun partTwo(): Int {
    val resourceUrl = Day01::class.java.classLoader.getResource("day01/part1.input")
    val input = resourceUrl?.readText() ?: ""
    val lines = input.lines()

    var numberOfTimesAtZero = 0
    var dial = 50
    for (line in lines) {
      if (line.isBlank()) continue

      val dir = if (line.startsWith('L')) -1 else 1
      val numberOfSteps = line.substring(1).toInt()

      for (unused in 0 until numberOfSteps) {
        dial = (dial + dir).mod(100).absoluteValue
        if (dial == 0) {
          numberOfTimesAtZero++
        }
      }
    }

    return numberOfTimesAtZero
  }
}