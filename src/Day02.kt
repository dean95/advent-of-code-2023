import kotlin.math.max

private fun main() {
    fun part1(input: List<String>): Int {
        val totalRedCubes = 12
        val totalGreenCubes = 13
        val totalBlueCubes = 14

        var result = 0

        outer@ for (game in input) {
            val parts = game.split(':', ';')
            val gameId = parts.first().filter(Char::isDigit).toInt()
            for (i in 1..parts.lastIndex) {
                for (subset in parts[i].split(',')) {
                    val (numberOfCubes, color) = subset.trim().split(' ')
                    when (color) {
                        "red" -> if (numberOfCubes.toInt() > totalRedCubes) continue@outer
                        "green" -> if (numberOfCubes.toInt() > totalGreenCubes) continue@outer
                        "blue" -> if (numberOfCubes.toInt() > totalBlueCubes) continue@outer
                    }
                }
            }
            result += gameId
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        for (game in input) {
            val parts = game.split(':', ';')

            var maxRed = Int.MIN_VALUE
            var maxGreen = Int.MIN_VALUE
            var maxBlue = Int.MIN_VALUE
            for (i in 1..parts.lastIndex) {
                for (subset in parts[i].split(',')) {
                    val (numberOfCubes, color) = subset.trim().split(' ')
                    when (color) {
                        "red" -> maxRed = max(maxRed, numberOfCubes.toInt())
                        "green" -> maxGreen = max(maxGreen, numberOfCubes.toInt())
                        "blue" -> maxBlue = max(maxBlue, numberOfCubes.toInt())
                    }
                }
            }
            result += (maxRed * maxGreen * maxBlue)
        }

        return result
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
