package com.bendb.aoc.year2025

object Day03 {
  private fun String.largestDigits(k: Int): Long {
    if (k >= length) return toLong()

    var result = 0L
    var start = 0
    var remaining = k

    while (remaining > 0) {
      val end = length - remaining

      var maxDigit = '0'
      var maxIndex = start
      for (i in start..end) {
        val digit = this[i]
        if (digit > maxDigit) {
          maxDigit = digit
          maxIndex = i
        }
      }

      result = result * 10 + maxDigit.digitToInt()
      start = maxIndex + 1
      remaining--
    }

    return result
  }

  fun partOne(input: String): Long {
    return input
      .trim()
      .lines()
      .filter { it.isNotBlank() }
      .sumOf { it.largestDigits(2) }
  }

  fun partTwo(input: String): Long {
    return input
      .trim()
      .lines()
      .sumOf { it.largestDigits(12) }
  }
}