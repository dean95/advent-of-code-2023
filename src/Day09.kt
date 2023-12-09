private fun main() {
    fun parseInput(input: List<String>) = input.map {
        it.split(' ').map(String::toInt)
    }

    fun part1(input: List<String>): Int {
        val histories = parseInput(input)

        fun calculate(input: List<Int>): Int {
            if (input.all { it == 0 }) return 0
            return calculate(input.zipWithNext { a, b -> b - a }) + input.last()
        }

        return histories.fold(0) { acc, history -> acc + calculate(history) }
    }

    fun part2(input: List<String>): Int {
        val histories = parseInput(input)

        fun calculate(input: List<Int>): Int {
            if (input.all { it == 0 }) return 0
            return input.first() - calculate(input.zipWithNext { a, b -> b - a })
        }

        return histories.fold(0) { acc, history -> acc + calculate(history) }
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
