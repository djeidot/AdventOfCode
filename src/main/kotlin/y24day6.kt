
fun y24day6() {
    val lines = readResource("y24day6.txt").toList()
    val matrix = lines.map { it.toCharArray() }
    val height = matrix.count()
    val width = matrix[0].count()

    fun Position.getValue() = matrix[this.y][this.x]
    fun Position.setValue(value: Char) { matrix[this.y][this.x] = value }

    var guard = Position(-1, -1)

    for (row in 0 until height) {
        for (col in 0 until width) {
            if (matrix[row][col] == '^') {
                guard = Position(col, row)
            }
        }
    }

    val previous = mutableSetOf<Pair<Position, Char>>()
    var iter = 1

    while (guard.x in 0 until width && guard.y in 0 until height) {
        println("Iteration $iter, guard at $guard")
        iter++

        fun getNextPositionAndDirection(position: Position): Pair<Position, Char>
        {
            var direction = position.getValue();

            var altDirection = 'x'
            while (true) {
                val nextPosition = position.copy();
                when (direction) {
                    '^' -> { nextPosition.y--; altDirection = '>' }
                    'v' -> { nextPosition.y++; altDirection = '<' }
                    '>' -> { nextPosition.x++; altDirection = 'v' }
                    '<' -> { nextPosition.x--; altDirection = '^' }
                    else -> throw IllegalStateException("Unrecognized direction ${direction}")
                }

                if (nextPosition.x !in 0 until width || nextPosition.y !in 0 until height) {
                    return Pair(nextPosition, direction)
                }

                if (nextPosition.getValue() !in "#O") {
                    return Pair(nextPosition, direction)
                }

                direction = altDirection
                if (direction == position.getValue()) {
                    throw IllegalStateException("Can't go anywhere")
                }
            }
        }

        val (nextPosition, nextDirection) = getNextPositionAndDirection(guard)

        if (nextPosition.x !in 0 until width || nextPosition.y !in 0 until height) {
            break
        }
        //checkObstacle(guard, guardDirection)

        guard.setValue('X')
        guard = nextPosition
        guard.setValue(nextDirection)
        val record = Pair(nextPosition, nextDirection)
        if (record in previous) {
            throw IllegalStateException("Yep, we're in a loop")
        }
        previous += record
    }

    val result = matrix.sumOf { it.count { it1 -> it1 == 'X' }}
    print("result = $result")
}

