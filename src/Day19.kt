private fun main() {
    fun part1(input: String): Int {
        val (workflowsString, ratings) = input.split("\n\n")

        val workflows = mutableMapOf<String, Pair<MutableList<Rule>, String>>()
        for (line in workflowsString.split('\n')) {
            val (name, rulesString) = line.dropLast(1).split('{')
            val rules = rulesString.split(',')
            workflows[name] = mutableListOf<Rule>() to rules.last()

            for (i in 0 until rules.lastIndex) {
                val (rule, targetWorkflow) = rules[i].split(':')
                workflows.getValue(name).first.add(
                    Rule(rule, targetWorkflow)
                )
            }
        }

        fun evalExpression(expr: String): Boolean {
            val operator = if ('<' in expr) '<' else '>'
            val parts = expr.split(operator)
            return when (operator) {
                '<' -> parts[0].toInt() < parts[1].toInt()
                '>' -> parts[0].toInt() > parts[1].toInt()
                else -> error("Unknown operator: $operator")
            }
        }

        fun accept(item: Map<Char, Int>, name: String = "in"): Boolean {
            if (name == "A") return true
            if (name == "R") return false

            val (rules, fallback) = workflows.getValue(name)
            for ((rule, target) in rules) {
                if (evalExpression(rule.replaceFirstChar { item.getValue(it).toString() })) {
                    return accept(item, target)
                }
            }

            return accept(item, fallback)
        }

        var result = 0

        for (line in ratings.split('\n').dropLast(1)) {
            val item = line.drop(1).dropLast(1).split(',').associate {
                it.first() to it.substring(2).toInt()
            }

            if (accept(item)) {
                result += item.values.sum()
            }
        }

        return result
    }

    fun part2(input: String): Long {
        val workflowsString = input.split("\n\n").first()

        val workflows = mutableMapOf<String, Pair<MutableList<Rule>, String>>()
        for (line in workflowsString.split('\n')) {
            val (name, rulesString) = line.dropLast(1).split('{')
            val rules = rulesString.split(',')
            workflows[name] = mutableListOf<Rule>() to rules.last()

            for (i in 0 until rules.lastIndex) {
                val (rule, targetWorkflow) = rules[i].split(':')
                workflows.getValue(name).first.add(
                    Rule(rule, targetWorkflow)
                )
            }
        }

        fun count(ranges: Map<Char, Pair<Int, Int>>, name: String = "in"): Long {
            val currentRanges = ranges.toMutableMap()
            if (name == "R") return 0
            if (name == "A") {
                var res = 1L
                currentRanges.values.forEach { (low, high) ->
                    res *= (high - low) + 1
                }
                return res
            }

            val (rules, fallback) = workflows.getValue(name)

            var total = 0L
            var allValuesProcessed = false
            for (rule in rules) {
                val expr = rule.expr
                val key = expr.first()
                val cmp = expr[1]
                val n = expr.substring(2).toInt()
                val (low, high) = currentRanges.getValue(key)

                var tRange: Pair<Int, Int>
                var fRange: Pair<Int, Int>
                if (cmp == '<') {
                    tRange = low to (n - 1)
                    fRange = n to high
                } else {
                    tRange = (n + 1) to high
                    fRange = low to n
                }
                if (tRange.first <= tRange.second) {
                    val rangesCopy = currentRanges.toMutableMap()
                    rangesCopy[key] = tRange
                    total += count(rangesCopy, rule.targetWorkflow)
                }
                if (fRange.first <= fRange.second) {
                    currentRanges[key] = fRange
                } else {
                    allValuesProcessed = true
                }
            }

            if (!allValuesProcessed) {
                total += count(currentRanges, fallback)
            }

            return total
        }

        return count("xmas".associate { it to (1 to 4000) })
    }

    val input = readText("Day19")
    println(part1(input))
    println(part2(input))
}

data class Rule(
    val expr: String,
    val targetWorkflow: String
)
