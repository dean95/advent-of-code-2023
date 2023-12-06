private fun main() {
    fun calculateWaysToWin(time: Long, distance: Long): Int {
        var remainingTime = time
        var speed = 0
        var waysToWin = 0
        while (remainingTime > 0) {
            if (speed * remainingTime > distance) waysToWin++
            remainingTime = time - ++speed
        }

        return waysToWin
    }

    fun part1(input: List<String>): Int {
        val (times, distances) = input.map {
            it.split("\\s+".toRegex()).drop(1).map(String::toLong)
        }

        return times.foldIndexed(1) { index, accumulator, time ->
            accumulator * calculateWaysToWin(time, distances[index])
        }
    }

    fun part2(input: List<String>): Int {
        val (time, distance) = input.map {
            it.split("\\s+".toRegex()).drop(1).joinToString("").toLong()
        }

        return calculateWaysToWin(time, distance)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
