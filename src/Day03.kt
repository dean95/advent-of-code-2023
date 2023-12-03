private fun main() {
    val dx = listOf(0, 0, -1, 1, -1, -1, 1, 1)
    val dy = listOf(-1, 1, 0, 0, -1, 1, -1, 1)

    fun inBounds(x: Int, y: Int, input: List<String>) = x in input.indices && y in input.first().indices

    fun part1(input: List<String>): Int {

        fun isAdjacentToSymbol(numberIndexes: List<Pair<Int, Int>>): Boolean {
            for ((x, y) in numberIndexes) {
                repeat(dx.size) {
                    val currentX = x + dx[it]
                    val currentY = y + dy[it]
                    if (inBounds(currentX, currentY, input)
                        && (!input[currentX][currentY].isDigit() && input[currentX][currentY] != '.')
                    ) return true
                }
            }
            return false
        }

        var result = 0

        fun addToResult(digitsIndices: List<Pair<Int, Int>>) {
            if (digitsIndices.isNotEmpty() && isAdjacentToSymbol(digitsIndices)) {
                result += digitsIndices.joinToString("") { (x, y) -> "${input[x][y]}" }.toInt()
            }
        }

        for (row in input.indices) {
            val digitsIndices = mutableListOf<Pair<Int, Int>>()
            for (column in input[row].indices) {
                if (input[row][column].isDigit()) {
                    digitsIndices.add(row to column)
                } else {
                    addToResult(digitsIndices)
                    digitsIndices.clear()
                }
            }
            addToResult(digitsIndices)
        }
        return result
    }

    fun part2(input: List<String>): Int {

        fun adjacentStarCoord(numberIndexes: List<Pair<Int, Int>>): Set<Pair<Int, Int>> {
            val result = mutableSetOf<Pair<Int, Int>>()
            for ((x, y) in numberIndexes) {
                repeat(dx.size) {
                    val currentX = x + dx[it]
                    val currentY = y + dy[it]
                    if (inBounds(currentX, currentY, input) && input[currentX][currentY] == '*') {
                        result.add(currentX to currentY)
                    }
                }
            }
            return result
        }

        val starToNumberMap = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        fun addToMap(digitsIndices: List<Pair<Int, Int>>) {
            if (digitsIndices.isNotEmpty()) {
                adjacentStarCoord(digitsIndices).forEach {
                    starToNumberMap.getOrPut(it, ::mutableListOf).add(
                        digitsIndices.joinToString("") { (x, y) -> "${input[x][y]}" }.toInt()
                    )
                }
            }
        }

        for (row in input.indices) {
            val digitsIndices = mutableListOf<Pair<Int, Int>>()
            for (column in input[row].indices) {
                if (input[row][column].isDigit()) {
                    digitsIndices.add(row to column)
                } else {
                    addToMap(digitsIndices)
                    digitsIndices.clear()
                }
            }
            if (digitsIndices.isNotEmpty()) {
                addToMap(digitsIndices)
            }
        }

        return starToNumberMap.values.filter { it.size == 2 }.sumOf { (first, second) -> first * second }
    }


    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
