
fun y24day6() {
    val lines = readResource("y24day6.txt").toMutableList()
    val height = lines.count()
    val width = lines[0].count()

    fun valueAt(pos: Position) = lines[pos.y][pos.x]

    var guard = Position(-1, -1)

    for (row in 0 until height) {
        for (col in 0 until width) {
            if (lines[row][col] == '^') {
                guard = Position(col, row)
            }
        }
    }

    val previous = mutableSetOf<Pair<Position, Char>>()
    var iter = 1

    while (guard.x in 0 until width && guard.y in 0 until height) {
        println("Iteration $iter, guard at $guard")
        iter++
        var guardDirection = valueAt(guard)
        lines[guard.y] = lines[guard.y].replaceRange(guard.x..guard.x, "X")
        assert(lines[guard.y].count() == width)
        var directionOk = false
        while (!directionOk) {
            var alternateDirection = 'x'
            val nextPosition = guard.copy()
            when (guardDirection) {
                '^' -> { nextPosition.y--; alternateDirection = '>' }
                'v' -> { nextPosition.y++; alternateDirection = '<' }
                '>' -> { nextPosition.x++; alternateDirection = 'v' }
                '<' -> { nextPosition.x--; alternateDirection = '^' }
                else -> throw IllegalStateException("Unrecognized guard direction $guardDirection")
            }

            if (nextPosition.x !in 0 until width || nextPosition.y !in 0 until height) {
                guard = nextPosition
                break
            }

            if (valueAt(nextPosition) == '#') {
                guardDirection = alternateDirection
                continue
            }

            directionOk = true
            guard = nextPosition
            lines[guard.y] = lines[guard.y].replaceRange(guard.x..guard.x, "$guardDirection")
            assert(lines[guard.y].count() == width)
            val record = Pair(nextPosition, guardDirection)
            if (record in previous) {
                throw IllegalStateException("Yep, we're in a loop")
            }
            previous += record
        }
    }

    val result = lines.sumOf { it.count { it1 -> it1 == 'X' }}
    print("result = $result")


}

