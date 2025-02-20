class Matrix(val matrix: List<CharArray>) {
    fun getAt(row: Int, col: Int) = matrix[row][col]
    fun getAt(pos: Position) = matrix[pos.y][pos.x]
    fun setAt(pos: Position, value: Char) { matrix[pos.y][pos.x] = value }
    val height get() = matrix.count()
    val width get() = matrix[0].count()

    fun copy() = Matrix(matrix.map { it.copyOf() })
}

fun y24day6() {
    val lines = readResource("y24day6.txt").toList()
    val matrix = Matrix(lines.map { it.toCharArray() })

    var guard = Position(-1, -1)

    for (row in 0 until matrix.height) {
        for (col in 0 until matrix.width) {
            if (matrix.getAt(row, col) == '^') {
                guard = Position(col, row)
            }
        }
    }

    val previous = mutableSetOf(Pair(guard, matrix.getAt(guard)))

    var iter = 1
    val obstacles = mutableSetOf<Position>()

    while (true) {
        println("Iteration $iter, guard at $guard facing ${matrix.getAt(guard)}")
        iter++

        val (nextPosition, nextDirection) = getNextPositionAndDirection(matrix, guard)

        if (nextPosition.x !in 0 until matrix.width || nextPosition.y !in 0 until matrix.height) {
            matrix.setAt(guard, 'X')
            break
        }

        if (matrix.getAt(nextPosition) != 'X') {
            checkObstacle(matrix.copy(), guard, nextPosition, previous.map { it.copy() }.toMutableSet())
                ?.let { obstacles.add(it) }
        }

        matrix.setAt(guard, 'X')
        guard = nextPosition
        matrix.setAt(guard, nextDirection)
        val record = Pair(nextPosition, nextDirection)
        if (record in previous) {
            throw IllegalStateException("Yep, we're in a loop")
        }
        previous += record
    }

    val result = matrix.matrix.sumOf { it.count { it1 -> it1 == 'X' }}
    println("result = $result")
    println("obstacles = ${obstacles.count()}")
}

fun checkObstacle(dreamMatrix: Matrix, guard: Position, obstacle: Position, previous: MutableSet<Pair<Position, Char>>): Position? {
    dreamMatrix.setAt(obstacle, 'O')
    var dreamGuard = guard.copy()

    while (true) {
        val (nextPosition, nextDirection) = getNextPositionAndDirection(dreamMatrix, dreamGuard)

        if (nextPosition.x !in 0 until dreamMatrix.width || nextPosition.y !in 0 until dreamMatrix.height) {
            return null
        }

        dreamGuard = nextPosition
        dreamMatrix.setAt(dreamGuard, nextDirection)
        val record = Pair(nextPosition, nextDirection)
//        println("\tx=${nextPosition.x}, y=${nextPosition.y}, dir=$nextDirection")
        if (record in previous) {
//            println("\tloop")
            return obstacle
        }
        previous += record
    }
}


fun getNextPositionAndDirection(matrix: Matrix, position: Position): Pair<Position, Char>
{
    var direction = matrix.getAt(position);

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

        if (nextPosition.x !in 0 until matrix.width || nextPosition.y !in 0 until matrix.height) {
            return Pair(nextPosition, direction)
        }

        if (matrix.getAt(nextPosition) !in "#O") {
            return Pair(nextPosition, direction)
        }

        direction = altDirection
        if (direction == matrix.getAt(position)) {
            throw IllegalStateException("Can't go anywhere")
        }
    }
}

