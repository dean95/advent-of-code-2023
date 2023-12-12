private fun main() {
    fun count(
        springs: String,
        sizes: List<Int>,
        lastSpringDamaged: Boolean = false,
        memo: MutableMap<Triple<String, List<Int>, Boolean>, Long> = mutableMapOf()
    ): Long {
        if (springs.isEmpty()) {
            return if (sizes.sum() == 0) 1 else 0
        }

        if (sizes.sum() == 0) {
            return if (springs.contains('#')) 0 else 1
        }

        val key = Triple(springs, sizes, lastSpringDamaged)
        if (key in memo) return memo.getValue(key)

        if (springs.first() == '#') {
            if (lastSpringDamaged && sizes.first() == 0) return 0
            val res = count(springs.drop(1), (listOf(sizes.first() - 1) + sizes.drop(1)), true, memo)
            memo[key] = res
            return res
        }

        if (springs.first() == '.') {
            if (lastSpringDamaged && sizes.first() > 0) return 0
            val res = count(springs.drop(1), sizes.takeIf { it.first() != 0 } ?: sizes.drop(1), false, memo)
            memo[key] = res
            return res
        }

        if (lastSpringDamaged) {
            val res = if (sizes.first() == 0) {
                count(springs.drop(1), sizes.drop(1), false, memo)
            } else {
                count(springs.drop(1), listOf(sizes.first() - 1) + sizes.drop(1), true, memo)
            }
            memo[key] = res
            return res
        }

        val res = count(springs.drop(1), sizes, false, memo) + count(
            springs.drop(1),
            listOf(sizes.first() - 1) + sizes.drop(1),
            true,
            memo
        )
        memo[key] = res

        return res
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (i in input) {
            val (springs, sizes) = i.split(' ')
            val sizeNumbers = sizes.split(',').map(String::toInt)
            total += count(springs, sizeNumbers)
        }

        return total
    }

    fun part2(input: List<String>): Long {

        var total: Long = 0
        for (i in input) {
            val (springs, sizes) = i.split(' ')
            val sizeNumbers = sizes.split(',').map(String::toInt)
            val repeatedSprings = Array(5) { springs }
            val repeatedSizes = mutableListOf<Int>()
            repeat(5) {
                repeatedSizes.addAll(sizeNumbers)
            }
            total += count(repeatedSprings.joinToString("?"), repeatedSizes)
        }

        return total
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
