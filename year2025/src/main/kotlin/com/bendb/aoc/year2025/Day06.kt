package com.bendb.aoc.year2025

object Day06 {
  fun partOne(input: String): Long {
      val grid = input
          .trim()
          .split("\n")
          .map {
              it
                  .trim()
                  .split("\\s+".toRegex())
          }

      val width = grid[0].size
      val height = grid.size


      var sum = 0L
      for (col in 0 until width) {
          val nums = mutableListOf<Long>()
          for (row in 0 until height - 1) {
              nums.add(grid[row][col].toLong())
          }
          val op = grid[height - 1][col]

          sum += when (op) {
              "*" -> nums.fold(1L) { acc, n -> acc * n }
              "+" -> nums.sum()
            else -> error("Unknown operator: $op")
          }
      }

      return sum
  }

  fun partTwo(input: String): Long {
      val grid = input.lines()
      val height = grid.size
      val opRow = height - 1
      val opsAndIndices =
          grid[opRow]
              .withIndex()
              .filter { (_, ch) -> ch == '*' || ch == '+' }

      val problems = mutableListOf<Problem>()
      for (ix in 1 until opsAndIndices.size) {
          val prev = opsAndIndices[ix - 1]
          val cur = opsAndIndices[ix]
          problems += Problem(grid, prev.value, prev.index, cur.index - 2)
      }
      problems += Problem(grid, opsAndIndices.last().value, opsAndIndices.last().index, -1)

      return problems.sumOf(Problem::solve)
  }


    data class Problem(
        val grid: List<String>,
        val op: Char,
        val index: Int,
        val endIndex: Int
    ) {
        fun solve(): Long {
            val nums = mutableListOf<Long>()
            val end = if (endIndex == -1) { grid.maxOf(String::length) - 1 } else endIndex

            for (col in end downTo index) {
                var num = 0L
                for (row in 0 until grid.size - 1) {
                    var ch = grid[row].getOrNull(col) ?: '0'
                    if (ch == ' ') {
                        if (num != 0L) {
                            break
                        }
                        ch = '0'
                    }
                    num *= 10
                    num += (ch - '0')
                }
                nums += num
            }

            return when (op) {
                '*' -> nums.fold(1L) { acc, n -> acc * n }
                '+' -> nums.sum()
                else -> error("Unknown operator: $op")
            }
        }
    }
}