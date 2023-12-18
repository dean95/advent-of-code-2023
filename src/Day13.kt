import kotlin.math.min

private fun main() {
    fun findMirrorPar1(input: List<String>): Int {
        for (i in 1..input.lastIndex) {
            val above = input.subList(0, i)
            val below = input.subList(i, input.size)

            val minLength = min(above.size, below.size)

            val aboveTrimmed = above.reversed().take(minLength)
            val belowTrimmed = below.take(minLength)

            if (aboveTrimmed == belowTrimmed) return i
        }

        return 0
    }

    fun findMirrorPart2(input: List<String>): Int {
        for (i in 1..input.lastIndex) {
            val above = input.subList(0, i)
            val below = input.subList(i, input.size)

            val diffChars = above.reversed().zip(below).sumOf {
                it.first.zip(it.second).count { (a, b) -> a != b }
            }
            if (diffChars == 1) return i
        }

        return 0
    }

    fun parseInput(input: List<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val currentList = mutableListOf<String>()
        input.forEach {
            if (it.isEmpty()) {
                result.add(currentList.toList())
                currentList.clear()
            } else {
                currentList.add(it)
            }
        }
        result.add(currentList.toList())
        return result
    }

    fun transpose(input: List<String>): List<String> {
        val grid = input.map { it.toCharArray().toList() }
        val transposedGrid = MutableList(grid.first().size) { MutableList(grid.size) { '.' } }

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                transposedGrid[j][i] = grid[i][j]
            }
        }

        return transposedGrid.map { it.joinToString("") }
    }

    fun part1(input: List<String>): Int {
        val patterns = parseInput(input)

        var result = 0

        patterns.forEach {
            result += findMirrorPar1(it) * 100
            result += findMirrorPar1(transpose(it))
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val patterns = parseInput(input)

        var result = 0

        patterns.forEach {
            result += findMirrorPart2(it) * 100
            result += findMirrorPart2(transpose(it))
        }

        return result
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
