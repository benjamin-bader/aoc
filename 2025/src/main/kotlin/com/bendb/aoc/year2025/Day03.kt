package com.bendb.aoc.year2025

import kotlin.streams.toList

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

  private fun String.twoLargestDigits(): Long {
    var minDigit = 0
    var maxSum = 0
    for (i in indices) {
      val a = this[i].digitToInt()
      if (a < minDigit) continue

      minDigit = a

      for (j in i + 1 until length) {
        val b = this[j].digitToInt()
        val num = a * 10 + b
        if (num > maxSum) {
          maxSum = num
        }
      }
    }

    return maxSum.toLong()
  }

  fun partOne(input: String): Long {
    return input
      .lines()
      .filter { it.isNotBlank() }
      .parallelStream()
      .map { it.largestDigits(2) }
      .reduce(0L, Long::plus)
  }

  fun partTwo(input: String): Long {
    return input
      .lines()
      .filter { it.isNotBlank() }
      .parallelStream()
      .map { it.largestDigits(12) }
      .reduce(0L, Long::plus)
  }
}