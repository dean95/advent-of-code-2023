private fun main() {

    fun countEnergizedTiles(grid: List<CharArray>, start: Beam): Int {
        val queue = ArrayDeque<Beam>()
        queue.addFirst(start)

        val visited = mutableSetOf<Beam>()

        while (queue.isNotEmpty()) {
            val beam = queue.removeLast()
            if (beam.position.x !in grid.indices || beam.position.y !in grid[0].indices) continue

            if (beam in visited) continue

            visited.add(beam)

            when (val tile = grid[beam.position.x][beam.position.y]) {
                '.' -> {
                    var (newX, newY) = beam.position
                    when (beam.direction) {
                        Direction.UP -> newX--
                        Direction.DOWN -> newX++
                        Direction.LEFT -> newY--
                        Direction.RIGHT -> newY++
                    }
                    queue.addFirst(beam.copy(position = Position(newX, newY)))
                }

                '/', '\\' -> {
                    val newDirection = when (beam.direction) {
                        Direction.UP -> if (tile == '/') Direction.RIGHT else Direction.LEFT
                        Direction.DOWN -> if (tile == '/') Direction.LEFT else Direction.RIGHT
                        Direction.LEFT -> if (tile == '/') Direction.DOWN else Direction.UP
                        Direction.RIGHT -> if (tile == '/') Direction.UP else Direction.DOWN
                    }
                    var (newX, newY) = beam.position
                    when (newDirection) {
                        Direction.UP -> newX--
                        Direction.DOWN -> newX++
                        Direction.LEFT -> newY--
                        Direction.RIGHT -> newY++
                    }
                    queue.addFirst(beam.copy(position = Position(newX, newY), direction = newDirection))
                }

                '|' -> {
                    var (newX, newY) = beam.position

                    when (beam.direction) {
                        Direction.UP -> {
                            newX--
                            queue.addFirst(beam.copy(position = Position(newX, newY)))
                        }

                        Direction.DOWN -> {
                            newX++
                            queue.addFirst(beam.copy(position = Position(newX, newY)))
                        }

                        Direction.LEFT, Direction.RIGHT -> {
                            queue.addFirst(beam.copy(position = Position(newX.inc(), newY), direction = Direction.DOWN))
                            queue.addFirst(beam.copy(position = Position(newX.dec(), newY), direction = Direction.UP))
                        }
                    }
                }

                '-' -> {
                    val (newX, newY) = beam.position
                    when (beam.direction) {
                        Direction.LEFT -> queue.addFirst(beam.copy(position = Position(newX, newY.dec())))
                        Direction.RIGHT -> queue.addFirst(beam.copy(position = Position(newX, newY.inc())))
                        Direction.UP, Direction.DOWN -> {
                            queue.addFirst(beam.copy(position = Position(newX, newY.dec()), direction = Direction.LEFT))
                            queue.addFirst(
                                beam.copy(
                                    position = Position(newX, newY.inc()),
                                    direction = Direction.RIGHT
                                )
                            )
                        }
                    }
                }
            }
        }

        return visited.map(Beam::position).toSet().size
    }

    fun part1(input: List<String>): Int {
        val grid = input.map(String::toCharArray)
        return countEnergizedTiles(grid, Beam(Position(0, 0), Direction.RIGHT))
    }


    fun part2(input: List<String>): Int {
        val grid = input.map(String::toCharArray)
        val starts = mutableListOf<Beam>()

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (i == 0) starts.add(Beam(Position(i, j), Direction.DOWN))
                if (i == grid.lastIndex) starts.add(Beam(Position(i, j), Direction.UP))
                if (j == 0) starts.add(Beam(Position(i, j), Direction.RIGHT))
                if (j == grid[i].lastIndex) starts.add(Beam(Position(i, j), Direction.LEFT))
            }
        }

        return starts.maxOf { countEnergizedTiles(grid, it) }
    }

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}

data class Position(
    val x: Int,
    val y: Int
)

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class Beam(
    val position: Position,
    val direction: Direction
)
