import kotlin.math.abs

private fun main() {
    fun part1(input: List<String>): Int {
        val points = mutableListOf(
            0 to 0
        )

        val dirs = mapOf(
            "U" to (-1 to 0),
            "D" to (1 to 0),
            "L" to (0 to -1),
            "R" to (0 to 1)
        )

        var b = 0

        input.forEach {
            val (d, n, _) = it.split(' ')
            val (dr, dc) = dirs.getValue(d)
            b += n.toInt()
            val (r, c) = points.last()
            points.add((r + dr * n.toInt()) to (c + dc * n.toInt()))
        }

        var area = 0
        for (i in 1..points.lastIndex) {
            area += points[i].first * (points[(i + 1) % points.size].second - points[i - 1].second)
        }

        area = abs(area) / 2

        val i = area - b / 2 + 1

        return i + b
    }

    fun part2(input: List<String>): Long {
        val points = mutableListOf(
            0L to 0L
        )

        val dirs = mapOf(
            "U" to (-1 to 0),
            "D" to (1 to 0),
            "L" to (0 to -1),
            "R" to (0 to 1)
        )

        var b: Long = 0

        input.forEach {
            val hexDigits = it.split(' ').last().removeSurrounding("(#", ")")
            val n = hexDigits.dropLast(1).toLong(16)
            val d = "RDLU"[hexDigits.last().digitToInt()].toString()
            val (dr, dc) = dirs.getValue(d)
            b += n
            val (r, c) = points.last()
            points.add((r + dr * n) to (c + dc * n))
        }

        var area: Long = 0
        for (i in 1..points.lastIndex) {
            area += points[i].first * (points[(i + 1) % points.size].second - points[i - 1].second)
        }

        area = abs(area) / 2

        val i = area - b / 2 + 1

        return i + b
    }

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
