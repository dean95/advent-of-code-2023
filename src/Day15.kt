private fun main() {
    fun hash(input: String): Int =
        input.fold(0) { acc, c ->
            var res = acc
            res += c.code
            res *= 17
            res %= 256
            res
        }

    fun part1(input: String): Int {
        val initializationSequence = input.filter { it != '\n' }.split(',')
        return initializationSequence.sumOf(::hash)
    }

    fun part2(input: String): Int {
        val boxes = MutableList(256) { mutableListOf<Lens>() }
        val initializationSequence = input.filter { it != '\n' }.split(',')

        for (step in initializationSequence) {
            val label = step.substringBeforeLast('=').substringBeforeLast('-')
            val boxIndex = hash(label)
            val box = boxes[boxIndex]
            if (step.endsWith('-')) {
                box.removeIf { it.label == label }
            } else {
                val focus = step.substringAfter('=').toInt()
                val existingLensIndex = box.indexOfFirst { it.label == label }
                if (existingLensIndex >= 0) {
                    box[existingLensIndex] = Lens(label, focus)
                } else {
                    box.add(Lens(label, focus))
                }
            }
        }

        var totalFocusingPower = 0

        for (i in boxes.indices) {
            for (j in boxes[i].indices) {
                totalFocusingPower += (1 + i) * (j + 1) * boxes[i][j].focus
            }
        }

        return totalFocusingPower
    }

    val input = readText("Day15")
    println(part1(input))
    println(part2(input))
}

data class Lens(
    val label: String,
    val focus: Int
)
