package com.bendb.aoc.year2025

fun <C : Collection<T>, T> C.pairwiseCombinations(): List<Pair<T, T>> {
    return this.flatMapIndexed { i, a ->
        this.drop(i + 1).map { b ->
            a to b
        }
    }
}
