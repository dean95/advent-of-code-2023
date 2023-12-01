private fun main() {
    val wordToDigitMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun findDigit(input: String, includeLetters: Boolean, first: Boolean): String {
        val range = if (first) input.indices else input.lastIndex downTo 0
        for (i in range) {
            val char = input[i]
            if (char.isDigit()) return char.toString()

            if (!includeLetters) continue
            for ((digitName, digitValue) in wordToDigitMap) {
                if (i - digitName.length + 1 >= 0 && input.substring(i - digitName.length + 1..i) == digitName)
                    return digitValue
            }
        }

        throw IllegalStateException()
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach {
            val firstDigit = findDigit(it, includeLetters = false, first = true)
            val lastDigit = findDigit(it, includeLetters = false, first = false)
            result += "$firstDigit$lastDigit".toInt()
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach {
            val firstDigit = findDigit(it, includeLetters = true, first = true)
            val lastDigit = findDigit(it, includeLetters = true, first = false)
            result += "$firstDigit$lastDigit".toInt()
        }

        return result
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
