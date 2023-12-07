private fun main() {
    fun part1(input: List<String>): Int {
        val cardsToStrength = mapOf(
            '2' to 1,
            '3' to 2,
            '4' to 3,
            '5' to 4,
            '6' to 5,
            '7' to 6,
            '8' to 7,
            '9' to 8,
            'T' to 9,
            'J' to 10,
            'Q' to 11,
            'K' to 12,
            'A' to 13
        )
        data class Hand(
            val hand: String
        ) : Comparable<Hand> {
            val type = toType()

            private fun toType(): Type {
                val charOccurrences = hand.groupingBy { it }.eachCount()
                val values = charOccurrences.values.sortedDescending()

                return when (values.first()) {
                    5 -> Type.FIVE_OF_KIND
                    4 -> Type.FOUR_OF_KIND
                    3 -> if (values[1] == 2) Type.FULL_HOUSE else Type.THREE_OF_KIND
                    2 -> if (values[1] == 2) Type.TWO_PAIR else Type.ONE_PAIR
                    else -> Type.HIGH_CARD
                }
            }

            override fun compareTo(other: Hand): Int {
                return when {
                    this.type.ordinal != other.type.ordinal -> this.type.ordinal - other.type.ordinal
                    else -> {
                        var isBigger = false
                        for (i in 0 until 5) {
                            if (cardsToStrength.getValue(this.hand[i]) > cardsToStrength.getValue(other.hand[i])) {
                                isBigger = true
                                break
                            } else if (cardsToStrength.getValue(this.hand[i]) < cardsToStrength.getValue(other.hand[i])) {
                                break
                            }
                        }

                        if (isBigger) 1 else -1
                    }
                }
            }
        }
        return input.asSequence().map { it.split(' ') }.map { Hand(it[0]) to it[1].toInt() }
            .sortedBy(Pair<Hand, Int>::first)
            .mapIndexed { index, (_, bid) -> (index + 1) * bid }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val cardsToStrength = mapOf(
            'J' to 0,
            '2' to 1,
            '3' to 2,
            '4' to 3,
            '5' to 4,
            '6' to 5,
            '7' to 6,
            '8' to 7,
            '9' to 8,
            'T' to 9,
            'Q' to 10,
            'K' to 11,
            'A' to 12
        )

        data class Hand(
            val hand: String
        ) : Comparable<Hand> {
            val type = toType()

            private fun toType(): Type {
                val charOccurrences = hand.groupingBy { it }.eachCount()
                val values = charOccurrences.values.sortedDescending()

                val jokerCount = charOccurrences['J'] ?: 0
                return when (values.first()) {
                    5 -> Type.FIVE_OF_KIND
                    4 -> when (jokerCount) {
                        4, 1 -> Type.FIVE_OF_KIND
                        else -> Type.FOUR_OF_KIND
                    }

                    3 -> when (jokerCount) {
                        3 -> if (values[1] == 2) Type.FIVE_OF_KIND else Type.FOUR_OF_KIND
                        2 -> Type.FIVE_OF_KIND
                        1 -> Type.FOUR_OF_KIND
                        else -> if (values[1] == 2) Type.FULL_HOUSE else Type.THREE_OF_KIND
                    }

                    2 -> when (jokerCount) {
                        2 -> if (values[1] == 2) Type.FOUR_OF_KIND else Type.THREE_OF_KIND
                        1 -> if (values[1] == 2) Type.FULL_HOUSE else Type.THREE_OF_KIND
                        else -> if (values[1] == 2) Type.TWO_PAIR else Type.ONE_PAIR
                    }

                    else -> if (jokerCount == 1) Type.ONE_PAIR else Type.HIGH_CARD
                }
            }

            override fun compareTo(other: Hand): Int {
                return when {
                    this.type.ordinal != other.type.ordinal -> this.type.ordinal - other.type.ordinal
                    else -> {
                        var isBigger = false
                        for (i in 0 until 5) {
                            if (cardsToStrength.getValue(this.hand[i]) > cardsToStrength.getValue(other.hand[i])) {
                                isBigger = true
                                break
                            } else if (cardsToStrength.getValue(this.hand[i]) < cardsToStrength.getValue(other.hand[i])) {
                                break
                            }
                        }

                        if (isBigger) 1 else -1
                    }
                }
            }
        }
        return input.asSequence().map { it.split(' ') }.map { Hand(it[0]) to it[1].toInt() }
            .sortedBy(Pair<Hand, Int>::first)
            .mapIndexed { index, (_, bid) -> (index + 1) * bid }
            .sum()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

enum class Type {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_KIND, FULL_HOUSE, FOUR_OF_KIND, FIVE_OF_KIND
}
