package com.bendb.aoc.year2025

object Day04 {
  fun partOne(input: String): Long {
    val grid = Grid(input)

    var count = 0
    val pointsToX = mutableListOf<Point>()
    for (p in grid.points) {
      if (grid[p] != '@') {
        continue
      }
      val neighbors = grid.neighborsOf(p)
      val neighborPaperRolls = neighbors.filter { grid[it] == '@' }
      if (neighborPaperRolls.size < 4) {
        ++count
        pointsToX += p
      }
    }

    for (p in pointsToX) {
      grid[p] = 'x'
    }

    println(grid)

    return count.toLong()
  }

  fun partTwo(input: String): Long {
    val grid = Grid(input)

    println("BEGINNING")
    println(grid)

    var totalRemoved = 0
    var removed = 0
    do {
      val pointsToRemove = mutableListOf<Point>()
      for (p in grid.points) {
        if (grid[p] != '@') {
          continue
        }
        val neighbors = grid.neighborsOf(p)
        val neighborPaperRolls = neighbors.count { grid[it] == '@' }
        if (neighborPaperRolls < 4) {
          pointsToRemove += p
        }
      }

      for (p in pointsToRemove) {
        grid[p] = '.'
      }

      removed = pointsToRemove.size
      totalRemoved += removed
    } while (removed > 0)

    println("-------------END-------------")
    println(grid)

    return totalRemoved.toLong()
  }
}