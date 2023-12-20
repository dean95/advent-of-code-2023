private fun main() {
    fun transpose(input: List<String>): List<String> {
        val grid = input.map { it.toCharArray() }
        val transposed = List(input.first().length) { CharArray(input.size) { '.' } }

        for (i in input.indices) {
            for (j in input[i].indices) {
                transposed[j][i] = grid[i][j]
            }
        }

        return transposed.map { it.joinToString("") }
    }

    fun tiltNorth(input: List<String>) = transpose(
        transpose(input).map {
            it.split("#").joinToString("#") { it.toList().sortedDescending().joinToString("") }
        }
    )

    fun tiltWest(input: List<String>) = input.map {
        it.split("#").joinToString("#") { it.toList().sortedDescending().joinToString("") }
    }

    fun tiltSouth(input: List<String>) = transpose(
        transpose(input).map {
            it.split("#").joinToString("#") { it.toList().sorted().joinToString("") }
        }
    )

    fun tiltEast(input: List<String>) = input.map {
        it.split("#").joinToString("#") { it.toList().sorted().joinToString("") }
    }

    fun cycle(input: List<String>): List<String> {
        val tiltedNorth = tiltNorth(input)
        val tiltedWest = tiltWest(tiltedNorth)
        val tiltedSouth = tiltSouth(tiltedWest)
        return tiltEast(tiltedSouth)
    }

    fun part1(input: List<String>): Int = tiltNorth(input)
        .foldIndexed(0) { index, acc, s ->
            acc + s.count { it == 'O' } * (input.size - index)
        }

    fun part2(input: List<String>): Int {
        var current = input
        val statesSet = mutableSetOf<List<String>>()
        val statesList = mutableListOf<List<String>>()
        for (i in 0..1000000000) {
            current = cycle(current)
            if (current in statesSet) {
                val cycleLength = statesList.size - statesList.indexOf(current)
                val remainingCycles = (1000000000 - i) % cycleLength
                current = statesList[statesList.indexOf(current) + remainingCycles - 1]
                return current.foldIndexed(0) { index, acc, s ->
                    acc + s.count { it == 'O' } * (input.size - index)
                }
            } else {
                statesSet.add(current)
                statesList.add(current)
            }
        }
        return current.foldIndexed(0) { index, acc, s ->
            acc + s.count { it == 'O' } * (input.size - index)
        }
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
