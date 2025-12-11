package com.bendb.aoc.year2025

import java.util.PriorityQueue
import java.util.concurrent.ArrayBlockingQueue

object Day08 {
  data class D(val dist: Double, val from: Point3, val to: Point3)
  fun partOne(input: String, numConnections: Int = 1000): Long {

    // Kruskal's algorithm - priority queue to find the lowest-weighted edges
    // (i.e. closest junction boxes), union-find to connect them.  We don't
    // actually need the graph, and we don't need to build the entire spanning
    // tree.

    val points = input.lines().filter { it.isNotBlank() }.map { line ->
      val (x, y, z) = line.split(',').map(String::toInt)
      Point3(x, y, z)
    }

    // UnionFind works in terms of ints, so map points to ints.
    val pointsToIndex = points.withIndex().associate { (ix, point) -> point to ix }

    val q = PriorityQueue<D>(compareBy { it.dist })
    for ((a, b) in points.pairwiseCombinations()) {
      q.add(D(euclidianDistance(a, b), a, b))
    }

    val uf = UnionFind(points.size)
    for (ix in 0 until numConnections) {
      val dist = q.poll()

      val px1 = pointsToIndex[dist.from]!!
      val px2 = pointsToIndex[dist.to]!!

      if (uf.find(px1) != uf.find(px2)) {
        uf.unite(px1, px2)
      }
    }

    val circuitSizes = mutableMapOf<Int, Int>()
    for (p in points) {
      val px = pointsToIndex[p]!!
      val rep = uf.find(px)
      circuitSizes[rep] = (circuitSizes[rep] ?: 0) + 1
    }

    return circuitSizes.values.sortedDescending().take(3).reduce(Int::times).toLong()
  }

  fun partTwo(input: String): Long {
    // Kruskal's algorithm - priority queue to find the lowest-weighted edges
    // (i.e. closest junction boxes), union-find to connect them.  We don't
    // actually need the graph, and we don't need to build the entire spanning
    // tree.

    val points = input.lines().filter { it.isNotBlank() }.map { line ->
      val (x, y, z) = line.split(',').map(String::toInt)
      Point3(x, y, z)
    }

    // UnionFind works in terms of ints, so map points to ints.
    val pointsToIndex = points.withIndex().associate { (ix, point) -> point to ix }

    val q = PriorityQueue<D>(compareBy { it.dist })
    for ((a, b) in points.pairwiseCombinations()) {
      q.add(D(euclidianDistance(a, b), a, b))
    }

    var numOfCircuits = points.size
    lateinit var lastConnection: D

    val uf = UnionFind(points.size)
    while (numOfCircuits > 1 && q.isNotEmpty()) {
      val dist = q.poll()

      val px1 = pointsToIndex[dist.from]!!
      val px2 = pointsToIndex[dist.to]!!

      if (uf.find(px1) != uf.find(px2)) {
        uf.unite(px1, px2)
        lastConnection = dist
        numOfCircuits--
      }
    }

    val (d, p1, p2) = lastConnection
    return p1.x.toLong() * p2.x.toLong()
  }

  class KD(
    points: List<Point3>,
  ) {
    private val root: Node

    init {
      require(points.isNotEmpty())

      root = Node(dim = 0, points = points)
    }

    fun nn(point: Point3): Point3? {
      TODO()
    }

    private class Node(
      val dim: Int,
      val point: Point3,
      val left: Node?,
      val right: Node?
    ) {

      fun nearest(point: Point3): Point3 {
        val (nextChild, otherChild) = if (point[dim] <= this.point[dim]) {
          left to right
        } else {
          right to left
        }

        var best = nextChild?.nearest(point) ?: this.point

        // check hypersphere intersection
        if (otherChild != null && euclidianDistance(otherChild.point, point) < euclidianDistance(best, point)) {
          val maybeBest = otherChild.nearest(point)
          if (euclidianDistance(maybeBest, point) < euclidianDistance(best, point)) {
            best = maybeBest
          }
        }

        return best
      }

      companion object {
        operator fun invoke(dim: Int, points: List<Point3>): Node {
          val sorted = points.sortedBy { it[dim] }
          val medianIndex = 0
          val smaller = sorted.subList(0, medianIndex)
          val larger = sorted.subList(medianIndex + 1, sorted.size)
          val nextDim = (dim + 1) % 3

          val left = if (smaller.isNotEmpty()) {
            Node(nextDim, smaller)
          } else {
            null
          }

          val right = if (larger.isNotEmpty()) {
            Node(nextDim, larger)
          } else {
            null
          }
          return Node(dim, sorted[medianIndex], left, right)
        }
      }
    }

    companion object {
      private operator fun Point3.get(dim: Int): Int {
        return when (dim) {
          0 -> x
          1 -> y
          2 -> z
          else -> error("bad dim: $dim")
        }
      }
    }
  }
}
