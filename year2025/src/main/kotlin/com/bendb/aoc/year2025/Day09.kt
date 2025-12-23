package com.bendb.aoc.year2025

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day09 {
  data class Segment(val a: Point, val b: Point) {
    val vertical = a.x == b.x
    val yRange: IntRange = min(a.y, b.y)..max(a.y, b.y)
    val xRange: IntRange = min(a.x, b.x)..max(a.x, b.x)
  }

  data class Rect(val a: Point, val b: Point) {
    val area: Long = (abs(a.x - b.x) + 1).toLong() * (abs(a.y - b.y) + 1).toLong()

    val lines: List<Segment>
      get() {
        val (minX, maxX) = min(a.x, b.x) to max(a.x, b.x)
        val (minY, maxY) = min(a.y, b.y) to max(a.y, b.y)

        return listOf(
          Segment(Point(minX, minY), Point(maxX, minY)),
          Segment(Point(maxX, minY), Point(maxX, maxY)),
          Segment(Point(minX, maxY), Point(maxX, maxY)),
          Segment(Point(minX, minY), Point(minX, maxY)),
        )
      }
  }

  fun partOne(input: String): Long {
    val points =
      input
        .lines()
        .filter { it.isNotBlank() }
        .map { it.split(",").map(String::toInt).let { (x, y) -> Point(x, y) } }

    return points
      .pairwiseCombinations()
      .maxOf { (a, b) -> abs(a.x - b.x + 1).toLong() * abs(a.y - b.y + 1) }
  }

  fun partTwo(input: String): Long {
    val points =
      input
        .lines()
        .filter { it.isNotBlank() }
        .map { it.split(",").map(String::toInt).let { (x, y) -> Point(x, y) } }

    val segments = mutableListOf<Segment>()
    for (ix in points.indices) {
      val from = points[ix]
      val to = if (ix == points.lastIndex) points[0] else points[ix+1]

      segments += if (to.x > from.x || to.y > from.y) Segment(from, to) else Segment(from, to)
    }

    return points
      .pairwiseCombinations()
      .map { (p1, p2) -> Rect(p1, p2) }
      .sortedByDescending(Rect::area)
      .toList()
      .stream()
      .filter { isValidRectangle(it, segments) }
      .findFirst().orElseThrow()
      .area
  }

  private fun isValidRectangle(rect: Rect, polygon: List<Segment>): Boolean {
    for (polyLine in polygon) {
      for (rectLine in rect.lines) {
        if (polyLine.vertical == rectLine.vertical) continue

        if (polyLine.vertical) {
          if (polyLine.a.x in rectLine.a.x + 1 until rectLine.b.x && rectLine.a.y in polyLine.yRange) {
            return false
          }
        } else {
          if (polyLine.a.y in rectLine.a.y + 1 until rectLine.b.y && rectLine.a.x in polyLine.xRange) {
            return false
          }
        }
      }
    }

    return true

    /*
    // General approach:
    // assume p1 and p2 are red (they are)
    // for each point in the rectangle they define:
    //   return false if windingNumber of that point shows exteriority
    // return true
    for (y in min(p1.y, p2.y)..max(p1.y, p2.y)) {
      for (x in min(p1.x, p2.x)..max(p1.x, p2.x)) {
        val p = Point(x, y)
        if (p == p1 || p == p2) {
          // p1 and p2 are safe, no need to test
          continue
        }

        if (windingNumber(p, verticals) == 0) {
          return false
        }
      }
    }
    return true
   */
  }

  fun windingNumber(p: Point, verticals: List<Segment>): Int {
    // This is a loose adaptation of David Alciatore's and Rick Miranda's "Point in Polygon" algorithm:
    // https://www.engr.colostate.edu/~dga/documents/papers/point_in_polygon.pdf
    //
    // It computes a winding number by scaling each vertex by it - pointBeingTested, and counting
    // the number of crossings of the *positive* y axis.

    var w = 0
    for ((from, to) in verticals) {
      if (from.x <= p.x) {
        // Vertical line will cross the negative x axis, or be on the same vertical as p.
        // We don't care about these.
        continue
      }

      val scaledFrom = from - p
      val scaledTo = to - p

      when {
        // Ascending cases
        scaledFrom.y < 0 && scaledTo.y == 0 -> w += 1
        scaledFrom.y < 0 && scaledTo.y > 0 -> w += 2
        scaledFrom.y == 0 && scaledTo.y > 0 -> w += 1

        // Descending cases
        scaledFrom.y > 0 && scaledTo.y == 0 -> w -= 1
        scaledFrom.y > 0 && scaledTo.y < 0 -> w -= 2
        scaledFrom.y == 0 && scaledTo.y < 0 -> w -= 1
      }
    }
    return w
  }
}

