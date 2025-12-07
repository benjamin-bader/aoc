package com.bendb.aoc.year2025

class Grid(val grid: List<CharArray>) {
  val height = grid.size
  val width = grid[0].size

  constructor(text: String) : this(text.lines().map { it.toCharArray() })

  val points: Sequence<Point>
    get() = sequence {
      for (y in 0 until height) {
        for (x in 0 until width) {
          yield(Point(x, y))
        }
      }
    }

  /**
   * Returns the columns of the grid as sequences of character lists, from top to bottom.
   */
  val columns: Sequence<List<Char>>
    get() = sequence {
      for (x in 0 until width) {
        val column = mutableListOf<Char>()
        for (y in 0 until height) {
          column.add(grid[y][x])
        }
        yield(column)
      }
    }


  operator fun get(x: Int, y: Int): Char {
    return grid[y][x]
  }

  operator fun get(point: Point): Char {
    return grid[point.y][point.x]
  }

  operator fun set(point: Point, char: Char) {
    grid[point.y][point.x] = char
  }

  operator fun contains(point: Point): Boolean {
    return point.x >= 0 && point.x < width && point.y >= 0 && point.y < height
  }

  fun neighborsOf(p: Point): List<Point> {
    return Point.NEIGHBORS.map { it + p }.filter { it in this }
  }

  override fun toString(): String {
    return buildString {
      for (y in 0 until height) {
        for (x in 0 until width) {
          append(grid[y][x])
        }
        appendLine()
      }
    }
  }
}
