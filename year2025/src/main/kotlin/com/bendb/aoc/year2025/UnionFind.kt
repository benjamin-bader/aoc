package com.bendb.aoc.year2025

class UnionFind private constructor(
  private val parent: IntArray,
  private val rank: IntArray,
) {
  constructor(size: Int) : this(
    parent = IntArray(size) { it },
    rank = IntArray(size) { 1 },
  )

  fun find(a: Int): Int {
    if (parent[a] == a) {
      return a
    }

    val rep = find(parent[a])

    // compress that path!
    parent[a] = rep

    return rep
  }

  fun unite(a: Int, b: Int) {
    var ra = find(a)
    var rb = find(b)
    if (ra == rb) {
      return
    }

    if (rank[ra] < rank[rb]) {
      val tmp = ra
      ra = rb
      rb = tmp
    }

    parent[rb] = ra
    rank[ra] += rank[rb]
  }
}
