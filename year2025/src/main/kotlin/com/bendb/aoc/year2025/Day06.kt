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
    val g = Grid(input)
    var sum = 0L
    val nums = mutableListOf<Long>()
    for (col in g.columns.toList().reversed()) {
      if (col.all { it == ' ' }) {
        nums.clear()
        continue
      }
      nums += col.toLong()

      when (col.last()) {
        '+' -> sum += nums.reduce(Long::plus)
        '*' -> sum += nums.reduce(Long::times)
      }
    }

    return sum
  }

  private fun List<Char>.toLong(): Long {
    val ix = this.indexOfFirst { it != ' ' }
    var num = 0L
    for (i in ix until this.size) {
      val c = this[i]
      if (c !in '0'..'9') {
        break
      }
      num = num * 10 + (c - '0')
    }
    return num
  }
}
