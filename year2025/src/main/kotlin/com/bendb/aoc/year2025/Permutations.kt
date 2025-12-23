package com.bendb.aoc.year2025

fun <C : Collection<T>, T> C.pairwiseCombinations(): Sequence<Pair<T, T>> {
  return sequence {
    for (i in indices) {
      for (j in i + 1 until size) {
        yield(elementAt(i) to elementAt(j))
      }
    }
  }
}
