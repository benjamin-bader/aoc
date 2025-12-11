package com.bendb.aoc.year2025

import kotlin.math.sqrt

data class Point3(
  val x: Int,
  val y: Int,
  val z: Int,
) : Comparable<Point3> {
  fun euclidianDistanceTo(other: Point3): Double {
    val px = x - other.x
    val py = y - other.y
    val pz = z - other.z
    return sqrt(((px.toLong() * px) + (py.toLong() * py) + (pz.toLong() * pz)).toDouble())
  }

  override fun compareTo(other: Point3): Int {
    return compareValuesBy(this, other, { it.x }, { it.y }, { it.z })
  }
}

fun euclidianDistance(a: Point3, b: Point3) = a.euclidianDistanceTo(b)
