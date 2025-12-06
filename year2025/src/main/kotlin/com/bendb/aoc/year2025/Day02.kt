package com.bendb.aoc.year2025

import kotlin.collections.map

object Day02 {
  data class Range(val start: Long, val end: Long)

  fun getRanges(input: String): List<Range> {
    return input.split(',')
      .filter { it.isNotBlank() }
      .map {
        val parts = it.split('-')
        Range(parts[0].toLong(), parts[1].toLong())
      }
      .sortedBy { it.start }
  }

  fun getAllNumbers(input: String): Sequence<String> {
    return getRanges(input)
      .asSequence()
      .flatMap { range ->
        (range.start .. range.end).map { it.toString() }
      }
  }

  private val divisors: Map<Int, List<Int>> = mapOf(
    1 to emptyList(),
    2 to listOf(1),
    3 to listOf(1),
    4 to listOf(1, 2),
    5 to listOf(1),
    6 to listOf(1, 2, 3),
    7 to listOf(1),
    8 to listOf(1, 2, 4),
    9 to listOf(1, 3),
    10 to listOf(1, 2, 5),
    11 to listOf(1),
    12 to listOf(1, 2, 3, 4, 6),
    13 to listOf(1),
    14 to listOf(1, 2, 7),
    15 to listOf(1, 3, 5),
    16 to listOf(1, 2, 4, 8),
    17 to listOf(1),
    18 to listOf(1, 2, 3, 6, 9),
    19 to listOf(1),
    20 to listOf(1, 2, 4, 5, 10),
    21 to listOf(1, 3, 7),
    22 to listOf(1, 2, 11),
    23 to listOf(1),
    24 to listOf(1, 2, 3, 4, 6, 8, 12),
    25 to listOf(1, 5),
    26 to listOf(1, 2, 13),
  )

  fun String.isSillySequence(): Boolean {
    val half = length / 2

    return substring(0, half) == substring(half)
  }

  fun String.isExtraSillySequence(): Boolean {
    for (divisor in divisors[length] ?: error("lol wut: length=$length")) {
      val chunk = substring(0, divisor)
      var found = true
      for (i in 0 until length step divisor) {
        if (substring(i, i + divisor) != chunk) {
          found = false
          break
        }
      }
      if (found) {
        return true
      }
    }

    return false
  }

  fun partOne(input: String): Long {
    return getAllNumbers(input)
      .filter { it.length % 2 == 0 }
      .filter { it.isSillySequence() }
      .sumOf { it.toLong() }
  }

  fun partTwo(input: String): Long {
    return getAllNumbers(input)
      .filter { it.isExtraSillySequence() }
      .sumOf { it.toLong() }
  }
}