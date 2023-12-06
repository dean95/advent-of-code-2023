import kotlin.math.max
import kotlin.math.min

private fun main() {
    fun part1(input: List<String>): Long {
        fun parseInput(input: List<String>): Pair<List<Long>, List<List<Triple<Long, Long, Long>>>> {
            val seeds = input.first().split(" ").drop(1).map(String::toLong)

            val maps = mutableListOf<List<Triple<Long, Long, Long>>>()
            val map = mutableListOf<Triple<Long, Long, Long>>()

            for (i in 2 until input.size) {
                if (input[i].isEmpty()) continue
                if (input[i].contains("map:")) {
                    if (map.isNotEmpty()) {
                        maps.add(map.toList())
                        map.clear()
                    }
                } else {
                    val numbers = input[i].split(" ").map(String::toLong)
                    map.add(Triple(numbers[0], numbers[1], numbers[2]))
                }
            }
            if (map.isNotEmpty()) {
                maps.add(map)
            }

            return seeds to maps
        }

        val (seeds, ranges) = parseInput(input)

        val locations = mutableListOf<Long>()
        for (seed in seeds) {
            var current = seed
            for (block in ranges) {
                for ((destStart, sourceStart, rangeLength) in block) {
                    if (sourceStart <= current && current < sourceStart + rangeLength) {
                        current = destStart + (current - sourceStart)
                        break
                    }
                }
            }
            locations.add(current)
        }

        return locations.min()
    }

    fun part2(input: List<String>): Long {
        fun parseInput(input: List<String>): Pair<List<LongRange>, List<List<Triple<Long, Long, Long>>>> {
            val seeds = input.first().split(" ").drop(1).map(String::toLong).chunked(2).map { it[0]..<it[0] + it[1] }

            val maps = mutableListOf<List<Triple<Long, Long, Long>>>()
            val map = mutableListOf<Triple<Long, Long, Long>>()

            for (i in 2 until input.size) {
                if (input[i].isEmpty()) continue
                if (input[i].contains("map:")) {
                    if (map.isNotEmpty()) {
                        maps.add(map.toList())
                        map.clear()
                    }
                } else {
                    val numbers = input[i].split(" ").map(String::toLong)
                    map.add(Triple(numbers[0], numbers[1], numbers[2]))
                }
            }
            if (map.isNotEmpty()) {
                maps.add(map)
            }

            return seeds to maps
        }

        val (seeds, ranges) = parseInput(input)

        val locations = mutableListOf<LongRange>()
        for (seed in seeds) {
            var currentStart = seed.first
            var currentEnd = seed.last
            for (block in ranges) {
                for ((destStart, sourceStart, rangeLength) in block) {
                    val overlapStart = max(currentStart, sourceStart)
                    val overlapEnd = min(currentEnd, sourceStart + rangeLength)
                    if (overlapStart < overlapEnd) {
                        currentStart = destStart + (overlapStart - sourceStart)
                        currentEnd = destStart + (overlapEnd - sourceStart)
                        break
                    }
                }
            }
            locations.add(currentStart..currentEnd)
        }

        return locations.minOf(LongRange::first)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
