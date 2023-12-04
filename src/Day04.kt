private fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { card ->
            val (winningSet, mySet) = card
                .substringAfter(':')
                .split('|')
                .map {
                    it.trim().split("\\s+".toRegex()).map(String::toInt).toSet()
                }
            var cardValue = 0
            mySet.forEach {
                if (it in winningSet) {
                    cardValue = if (cardValue == 0) 1 else cardValue * 2
                }
            }
            result += cardValue
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val scratchcardsCount = IntArray(input.size) { 1 }
        input.forEachIndexed { index, card ->
            val (winningSet, mySet) = card
                .substringAfter(':')
                .split('|')
                .map {
                    it.trim().split("\\s+".toRegex()).map(String::toInt).toSet()
                }
            var matchingNumbersCount = 0
            mySet.forEach {
                if (it in winningSet) {
                    matchingNumbersCount++
                }
            }
            repeat(scratchcardsCount[index]) {
                repeat(matchingNumbersCount) {
                    scratchcardsCount[index + it + 1]++
                }
            }
        }
        return scratchcardsCount.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
