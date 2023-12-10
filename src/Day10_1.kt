import kotlin.math.max

private fun main() {
    data class Cell(val type: Char, val x: Int, val y: Int)

    fun getDirections(cell: Cell, grid: List<List<Cell>>): Pair<List<Int>, List<Int>> {
        val dx = mutableListOf<Int>()
        val dy = mutableListOf<Int>()
        when (cell.type) {
            '|' -> {
                dx.addAll(listOf(-1, 1))
                dy.addAll(listOf(0, 0))
            }

            '-' -> {
                dx.addAll(listOf(0, 0))
                dy.addAll(listOf(-1, 1))
            }

            'L' -> {
                dx.addAll(listOf(-1, 0))
                dy.addAll(listOf(0, 1))
            }

            'J' -> {
                dx.addAll(listOf(-1, 0))
                dy.addAll(listOf(0, -1))
            }

            '7' -> {
                dx.addAll(listOf(1, 0))
                dy.addAll(listOf(0, -1))
            }

            'F' -> {
                dx.addAll(listOf(1, 0))
                dy.addAll(listOf(0, 1))
            }

            'S' -> {
                val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                for ((currentDx, currentDy) in directions) {
                    val newX = cell.x + currentDx
                    val newY = cell.y + currentDy
                    if (newX in grid.indices && newY in grid.first().indices && grid[newX][newY].type != '.') {
                        // Check if the neighbor also has 'S' as a neighbor
                        val neighborDirections = getDirections(grid[newX][newY], grid)
                        val neighborDx = neighborDirections.first
                        val neighborDy = neighborDirections.second
                        for (i in neighborDx.indices) {
                            val neighborX = newX + neighborDx[i]
                            val neighborY = newY + neighborDy[i]
                            if (neighborX in grid.indices && neighborY in grid.first().indices && grid[neighborX][neighborY].type == 'S') {
                                dx.add(currentDx)
                                dy.add(currentDy)
                                break
                            }
                        }
                    }
                }
            }
        }

        return dx to dy
    }

    fun part1(input: List<String>): Int {
        val grid = input.mapIndexed { x, line ->
            line.mapIndexed { y, char ->
                Cell(char, x, y)
            }
        }

        val start = grid.flatten().find { it.type == 'S' }!!

        fun bfs(start: Cell): Int {

            fun isPartOfLoop(cell: Cell, dx: List<Int>, dy: List<Int>): Boolean {
                var count = 0
                repeat(dx.size) {
                    val newX = cell.x + dx[it]
                    val newY = cell.y + dy[it]
                    if (newX in grid.indices && newY in grid.first().indices && grid[newX][newY].type != '.') {
                        count++
                    }
                }

                return count == 2
            }

            val visited = mutableSetOf<Cell>()

            val queue = ArrayDeque<Pair<Cell, Int>>()
            queue.add(start to 0)

            var maxDistance = 0
            while (queue.isNotEmpty()) {
                val (current, distance) = queue.removeLast()
                if (current in visited) continue
                visited.add(current)

                maxDistance = max(maxDistance, distance)

                val (currentDx, currentDy) = getDirections(current, grid)

                repeat(currentDx.size) {
                    val newX = current.x + currentDx[it]
                    val newY = current.y + currentDy[it]


                    if (newX !in grid.indices || newY !in grid.first().indices) return@repeat
                    val nextCell = grid[newX][newY]

                    val (newDx, newDy) = getDirections(nextCell, grid)

                    if (nextCell !in visited && isPartOfLoop(nextCell, newDx, newDy)) {
                        queue.addFirst(nextCell to distance + 1)
                    }
                }
            }
            return maxDistance
        }

        return bfs(start)
    }

    val input = readInput("Day10")
    println(part1(input))
}
