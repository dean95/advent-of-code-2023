private fun main() {
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

    fun applyMapping(sourceNumber: Long, mappingRule: List<Triple<Long, Long, Long>>): Long {
        for ((destStart, sourceStart, rangeLength) in mappingRule) {
            if (sourceStart <= sourceNumber && sourceNumber < sourceStart + rangeLength) {
                return destStart + (sourceNumber - sourceStart)
            }
        }
        return sourceNumber
    }

    fun mapSeedToLocation(seed: Long, mappings: List<List<Triple<Long, Long, Long>>>): Long {
        var currentValue = seed
        for (mapping in mappings) {
            currentValue = applyMapping(currentValue, mapping)
        }
        return currentValue
    }

    fun findMinLocation(seeds: List<Long>, mappings: List<List<Triple<Long, Long, Long>>>): Long =
        seeds.minOfOrNull { mapSeedToLocation(it, mappings) } ?: error("")

    fun part1(input: List<String>): Long {
        val (seeds, mappings) = parseInput(input)
        return findMinLocation(seeds, mappings)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    println(part1(input))
}
