enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun day12() {
    val lines = readResource("day12.txt")

    data class Position(val r: Int, val c: Int) {
        val height: Char = lines[r][c]

        fun getNeighbour(direction: Direction): Position? {
            val neighbour = when (direction) {
                Direction.UP -> if (r == 0) null else Position(r - 1, c)
                Direction.DOWN -> if (r == lines.lastIndex) null else Position(r + 1, c)
                Direction.LEFT -> if (c == 0) null else Position(r, c - 1)
                Direction.RIGHT -> if (c == lines[0].lastIndex) null else Position(r, c + 1)
            } ?: return null

            val adjustedHeight = when (height) { 'E' -> 'z'; 'S' -> 'a'; else -> height }.code
            val adjustedNeighbourHeight = when (neighbour.height) { 'E' -> 'z'; 'S' -> 'a'; else -> neighbour.height }.code
            return if (adjustedNeighbourHeight >= adjustedHeight - 1) neighbour else null
        }

        override fun toString(): String = "[$r, $c, $height]"
    }

    val steps = mutableListOf<List<Position>>()

    var startingPosition = Position(0, 0)
    row@for (r in lines.indices) col@for (c in lines[r].indices) {
        if (lines[r][c] == 'E') {
            startingPosition = Position(r, c)
            break@row
        }
    }

    println("Starting position: $startingPosition")

    fun getNextSteps(step: Position): List<Position> {
        val nextSteps = mutableListOf<Position>()
        for (direction in Direction.values()) {
            val nextStep = step.getNeighbour(direction)
            if (nextStep != null) {
                nextSteps.add(nextStep)
            }
        }
        return nextSteps
    }

    steps.add(getNextSteps(startingPosition))

    var stepCount = 0
    while (steps[stepCount].none { it.height == 'a' } && stepCount < 1000) {
        println("Iteration $stepCount, steps: ${steps[stepCount]}")
        val nextSteps = mutableSetOf<Position>()
        for (step in steps[stepCount]) {
            nextSteps.addAll(getNextSteps(step))
        }
        nextSteps.removeAll { it in steps.flatten() }
        steps.add(nextSteps.toList())
        stepCount++
    }
    stepCount++

    println("Number of steps: $stepCount")
}