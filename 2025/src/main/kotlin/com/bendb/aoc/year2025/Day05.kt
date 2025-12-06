package com.bendb.aoc.year2025

object Day05 {
  fun partOne(input: String): Long {
    val lines = input.trim().lines()
    val ranges = mutableListOf<LongRange>()
    val ids = mutableListOf<Long>()

    var ix = 0
    while (ix < lines.size) {
      val line = lines[ix]
      ++ix
      if (line.isBlank()) {
        break
      }

      val (small, large) = line.split("-").map(String::toLong)
      ranges += small..large
    }

    while (ix < lines.size) {
      val line = lines[ix]
      ++ix
      if (line.isBlank()) {
        break
      }

      ids += line.toLong()
    }

    return ids.count { id -> ranges.any { id in it } }.toLong()
  }

  fun partTwo(input: String): Long {
    val lines = input.trim().lines()
    val ranges = mutableListOf<LongRange>()

    for (line in lines) {
      if (line.isBlank()) {
        break
      }

      val (small, large) = line.split("-").map(String::toLong)
      ranges += small..large
    }

    ranges.sortBy { it.first }

    val mergedRanges = mutableListOf<LongRange>()
    for (range in ranges) {
      if (mergedRanges.isEmpty()) {
        mergedRanges += range
        continue
      }

      val lastRange = mergedRanges.last()
      if (range.first <= lastRange.last) {
        mergedRanges.removeLast()
        mergedRanges += minOf(lastRange.first, range.first)..maxOf(lastRange.last, range.last)
      } else {
        mergedRanges += range
      }
    }

    return mergedRanges.sumOf { it.last - it.first + 1 }
  }
}