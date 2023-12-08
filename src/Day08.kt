private fun main() {

    fun part1(input: List<String>): Int {
        val instructions = input.first()

        val network = mutableMapOf<String, Pair<String, String>>()

        for (i in 2 until input.size) {
            val (value, neighbours) = input[i].split(" = ")
            val (left, right) = neighbours.removeSurrounding("(", ")").split(", ")
            network[value] = left to right
        }


        var result = 0
        var current = "AAA"

        while (current != "ZZZ") {
            val instruction = instructions[result % instructions.length]
            current = if (instruction == 'L') network.getValue(current).first else network.getValue(current).second
            result++
        }

        return result
    }

    fun leastCommonMultiple(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun part2(input: List<String>): Long {
        val instructions = input.first()

        val network = mutableMapOf<String, Pair<String, String>>()

        for (i in 2 until input.size) {
            val (value, neighbours) = input[i].split(" = ")
            val (left, right) = neighbours.removeSurrounding("(", ")").split(", ")
            network[value] = left to right
        }

        val startingNodes = network.keys.filter { it.last() == 'A' }

        val counts = startingNodes.map { value ->
            var result = 0L
            var current = value

            while (current.last() != 'Z') {
                instructions.forEach {
                    current =
                        if (it == 'L') network.getValue(current).first else network.getValue(current).second
                }
                result += instructions.length
            }
            result
        }

        return counts.reduce { acc, i -> leastCommonMultiple(acc, i) }
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
