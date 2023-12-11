import kotlin.math.max
import kotlin.math.min

private fun main() {

    fun calculate(input: List<String>, scale: Int): Long {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        val rowsWithHash = mutableSetOf<Int>()
        val colsWithHash = mutableSetOf<Int>()
        for (row in input.indices) {
            for (column in input[row].indices) {
                if (input[row][column] == '#') {
                    galaxies.add(row to column)
                    rowsWithHash.add(row)
                    colsWithHash.add(column)
                }
            }
        }

        var total: Long = 0

        for (i in 0 until galaxies.size) {
            for (j in i + 1 until galaxies.size) {
                val (startRow, startCol) = galaxies[i]
                val (endRow, endCol) = galaxies[j]
                for (r in min(startRow, endRow) until max(startRow, endRow)) {
                    total += if (r !in rowsWithHash) scale else 1
                }
                for (c in min(startCol, endCol) until max(startCol, endCol)) {
                    total += if (c !in colsWithHash) scale else 1
                }
            }
        }

        return total
    }

    fun part1(input: List<String>): Long = calculate(input, 2)

    fun part2(input: List<String>): Long = calculate(input, 1000000)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
