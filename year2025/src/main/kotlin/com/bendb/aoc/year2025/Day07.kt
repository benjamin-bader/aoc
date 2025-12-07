package com.bendb.aoc.year2025

object Day07 {
  fun partOne(input: String): Long {
    val set = mutableSetOf<Int>()
    val grid = Grid(input)
    var timesSplit = 0
    for (row in grid.rows) {
      for ((ix, char) in row.withIndex()) {
        when (char) {
          'S' -> set.add(ix)
          '^' -> {
            if (ix in set) {
              ++timesSplit
              val left = ix - 1
              val right = ix + 1
              set.remove(ix)
              if (left >= 0) {
                set.add(left)
              }

              if (right < row.size) {
                set.add(right)
              }
            }
          }
        }
      }
    }
    return timesSplit.toLong()
  }

  fun partTwo(input: String): Long {
    val grid = Grid(input)
    val dp = Array(grid.height) {
      Array(grid.width) { 0L }
    }

    dp[0][grid.rows.first().indexOf('S')] = 1

    for (rowIndex in 1 until grid.height) {
      for (colIndex in 0 until grid.width) {
        val ch = grid[Point(x = colIndex, y = rowIndex)]
        when (ch) {
          '.' -> dp[rowIndex][colIndex] += dp[rowIndex-1][colIndex]
          '^' -> {
            val left = colIndex-1
            val right = colIndex+1
            if (left >= 0) {
              dp[rowIndex][left] += dp[rowIndex-1][colIndex]
            }
            if (right < grid.width) {
              dp[rowIndex][right] += dp[rowIndex-1][colIndex]
            }
          }
        }
      }
    }

    return dp.last().sum()
  }
}
