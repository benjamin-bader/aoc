package com.bendb.aoc.year2025

data class Point(val x: Int, val y: Int) {
  operator fun plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
  }

  operator fun minus(other: Point): Point {
    return Point(x - other.x, y - other.y)
  }

  companion object {
    val NEIGHBORS = listOf(
      Point(-1, -1), Point(0, -1), Point(1, -1),
      Point(-1, 0),                        Point(1, 0),
      Point(-1, 1), Point(0, 1), Point(1, 1),
    )
  }
}
