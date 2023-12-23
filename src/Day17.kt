import java.util.PriorityQueue

private fun main() {


    fun part1(input: List<String>): Int {
        val graph = input.map { it.map(Char::digitToInt) }

        val dx = intArrayOf(0, 1, 0, -1)
        val dy = intArrayOf(1, 0, -1, 0)

        val visited = mutableSetOf<State>()

        val pq = PriorityQueue(compareBy(Block::heatLoss))
        pq.add(Block(0, State(0, 0, 0, 0, 0)))

        while (pq.isNotEmpty()) {
            val (currentHeatLoss, state) = pq.remove()

            if (state in visited) continue

            visited.add(state)

            val (r, c, dR, dC, moves) = state

            if (r == graph.lastIndex && c == graph[r].lastIndex) return currentHeatLoss

            if (moves < 3 && dR to dC != 0 to 0) {
                val newRow = r + dR
                val newCol = c + dC
                if (newRow in graph.indices && newCol in graph.first().indices) {
                    pq.add(
                        Block(
                            heatLoss = currentHeatLoss + graph[newRow][newCol],
                            state = State(
                                newRow, newCol, dR, dC, moves + 1
                            )
                        )
                    )
                }
            }

            repeat(4) {
                val ndr = dx[it]
                val ndc = dy[it]

                if (ndr to ndc != dR to dC && ndr to ndc != -dR to -dC) {
                    val newRow = r + ndr
                    val newCol = c + ndc

                    if (newRow in graph.indices && newCol in graph.first().indices) {
                        pq.add(
                            Block(
                                heatLoss = currentHeatLoss + graph[newRow][newCol],
                                state = State(
                                    newRow, newCol, ndr, ndc, 1
                                )
                            )
                        )
                    }
                }
            }
        }

        error("Illegal state")
    }

    fun part2(input: List<String>): Int {
        val graph = input.map { it.map(Char::digitToInt) }

        val dx = intArrayOf(0, 1, 0, -1)
        val dy = intArrayOf(1, 0, -1, 0)

        val visited = mutableSetOf<State>()

        val pq = PriorityQueue(compareBy(Block::heatLoss))
        pq.add(Block(0, State(0, 0, 0, 0, 0)))

        while (pq.isNotEmpty()) {
            val (currentHeatLoss, state) = pq.remove()

            if (state in visited) continue

            visited.add(state)

            val (r, c, dR, dC, moves) = state

            if (r == graph.lastIndex && c == graph[r].lastIndex && moves >= 4) return currentHeatLoss

            if (moves < 10 && dR to dC != 0 to 0) {
                val newRow = r + dR
                val newCol = c + dC
                if (newRow in graph.indices && newCol in graph.first().indices) {
                    pq.add(
                        Block(
                            heatLoss = currentHeatLoss + graph[newRow][newCol],
                            state = State(
                                newRow, newCol, dR, dC, moves + 1
                            )
                        )
                    )
                }
            }

            if (moves >= 4 || dR to dC == 0 to 0) {
                repeat(4) {
                    val ndr = dx[it]
                    val ndc = dy[it]

                    if (ndr to ndc != dR to dC && ndr to ndc != -dR to -dC) {
                        val newRow = r + ndr
                        val newCol = c + ndc

                        if (newRow in graph.indices && newCol in graph.first().indices) {
                            pq.add(
                                Block(
                                    heatLoss = currentHeatLoss + graph[newRow][newCol],
                                    state = State(
                                        newRow, newCol, ndr, ndc, 1
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }

        error("Illegal state")
    }

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}

data class Block(
    val heatLoss: Int,
    val state: State
)

data class State(
    val row: Int,
    val column: Int,
    val dRow: Int,
    val dCol: Int,
    val moves: Int
)
