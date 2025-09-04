import kotlin.IllegalArgumentException

fun y24day15() {
    val lines = readResource("y24day15.txt")

    var robot = Position(0, 0)

    val lines2 = lines.takeWhile { it.startsWith('#') }.map { line -> line
        .replace("#", "##")
        .replace("O", "[]")
        .replace(".", "..")
        .replace("@", "@.")
    }

    val map = Matrix(lines.takeWhile { it.startsWith('#') }.map { it.toCharArray() })
    val map2 = Matrix(lines2.map { it.toCharArray() })
    val moves = lines.dropWhile { it.startsWith('#') || it.startsWith(' ') }.joinToString("").toCharArray().map {
        when (it) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> { throw IllegalArgumentException() }
        }
    }

    robot = map.findFirst('@')
    map.setAt(robot, '.')

    fun printMap(mmap: Matrix) {
        println()
        for (r in 0 until mmap.height) {
            for (c in 0 until mmap.width) {
                print(mmap.getAt(Position(r, c)))
            }
            println()
        }
    }

    fun moveBoxes(pos: Position, dir: Direction): Boolean {
        val pos1 = pos.offset(dir)
        when (map.getAt(pos1)) {
            '#' -> return false
            '.' -> {
                map.setAt(pos1, 'O')
                return true
            }
            'O' -> return moveBoxes(pos1, dir)
        }
        throw IllegalArgumentException("Unrecognized character at $pos1: ${map.getAt(pos1)}")
    }

    for (move in moves) {
        val newRobotPos = robot.offset(move)
        val objectAtPos = map.getAt(newRobotPos)
        when (objectAtPos) {
            '#' -> {} // don't move
            '.' -> { robot = newRobotPos }
            'O' -> {
                if (moveBoxes(newRobotPos, move)) {
                    map.setAt(newRobotPos, '.')
                    robot = newRobotPos
                }
            }
        }
    }

    var gpsSum = 0
    val boxList = map.findAll('O')
    boxList.forEach { gpsSum += 100 * it.r + it.c }

    print("GPS sum 1: $gpsSum")

    // Set robot for part 2
    robot = map2.findFirst('@');
    map2.setAt(robot, '.')

    fun canMoveBoxes2(pos: Position, dir: Direction): Boolean {
        val pos1 = pos.offset(dir)
        when (map2.getAt(pos1)) {
            '#' -> return false
            '.' -> return true
            '[' -> return when (dir) {
                Direction.LEFT, Direction.RIGHT -> canMoveBoxes2(pos1, dir)
                Direction.UP, Direction.DOWN -> canMoveBoxes2(pos1, dir) && canMoveBoxes2(pos1.offset(Direction.RIGHT), dir)
            }
            ']' -> return when (dir) {
                Direction.LEFT, Direction.RIGHT -> canMoveBoxes2(pos1, dir)
                Direction.UP, Direction.DOWN -> canMoveBoxes2(pos1, dir) && canMoveBoxes2(pos1.offset(Direction.LEFT), dir)
            }
        }
        throw IllegalArgumentException("Unrecognized character at $pos1: ${map2.getAt(pos1)}")
    }

    fun canMoveBoxes3(posL: Position, posR: Position, dir: Direction): Boolean {
        val pos1L = posL.offset(dir)
        val pos1R = posR.offset(dir)

        when (dir) {
            Direction.LEFT -> {
                val char = map2.getAt(pos1L)
                if (char == '#' || char == '[') {
                    return false
                }
                if (char == ']') {
                    // move other boxes first
                    return canMoveBoxes3(pos1L.offset(dir), pos1L, dir)
                }
            }
            Direction.RIGHT -> {
                val char = map2.getAt(pos1R)
                if (char == '#' || char == ']') {
                    return false
                }
                if (char == '[') {
                    // move other boxes first
                    return canMoveBoxes3(pos1R, pos1R.offset(dir), dir)
                }
            }
            Direction.UP, Direction.DOWN ->
            {
                val char1L = map2.getAt(pos1L)
                val char1R = map2.getAt(pos1R)
                return if (char1L == '#' || char1R == '#') {
                    false
                } else if (char1L == '[' && char1R == ']') {
                    canMoveBoxes3(pos1L, pos1R, dir)
                } else {
                    (if (char1L == ']') canMoveBoxes3(pos1L.offset(Direction.LEFT), pos1L, dir) else true)
                        && (if (char1R == '[') canMoveBoxes3(pos1R, pos1R.offset(Direction.RIGHT), dir) else true)
                }
            }
        }
        return true
    }


    fun moveBoxes2(posL: Position, posR: Position, dir: Direction) {
        val pos1L = posL.offset(dir)
        val pos1R = posR.offset(dir)

        when (dir) {
            Direction.LEFT -> {
                val char = map2.getAt(pos1L)
                if (char == '#' || char == '[') {
                    throw IllegalArgumentException("Didn't expect to see a '$char' at this stage ($pos1L, $dir)")
                }
                if (char == ']') {
                    // move other boxes first
                    moveBoxes2(pos1L.offset(dir), pos1L, dir)
                }

                // move box
                map2.setAt(pos1L, '[')
                map2.setAt(pos1R, ']')
                map2.setAt(posR, '.')
            }
            Direction.RIGHT -> {
                val char = map2.getAt(pos1R)
                if (char == '#' || char == ']') {
                    throw IllegalArgumentException("Didn't expect to see a '$char' at this stage ($pos1R, $dir)")
                }
                if (char == '[') {
                    // move other boxes first
                    moveBoxes2(pos1R, pos1R.offset(dir), dir)
                }
                // move box
                map2.setAt(pos1L, '[')
                map2.setAt(pos1R, ']')
                map2.setAt(posL, '.')
            }
            Direction.UP, Direction.DOWN ->
            {
                val char1L = map2.getAt(pos1L)
                val char1R = map2.getAt(pos1R)
                if (char1L == '#' || char1R == '#') {
                    throw IllegalArgumentException("Didn't expect to see a '#' at this stage ($pos1L, $pos1R, $dir)")
                } else if (char1L == '[' && char1R == ']') {
                    moveBoxes2(pos1L, pos1R, dir)
                } else {
                    // move other boxes first
                    if (char1L == ']') {
                        moveBoxes2(pos1L.offset(Direction.LEFT), pos1L, dir)
                    }
                    if (char1R == '[') {
                        moveBoxes2(pos1R, pos1R.offset(Direction.RIGHT), dir)
                    }
                }
                map2.setAt(pos1L, '[')
                map2.setAt(pos1R, ']')
                map2.setAt(posL, '.')
                map2.setAt(posR, '.')
            }
        }
    }

    printMap(map2)

    for (move in moves) {
        val newRobotPos = robot.offset(move)
        val objectAtPos = map2.getAt(newRobotPos)
        when (objectAtPos) {
            '#' -> {} // don't move
            '.' -> { robot = newRobotPos }
            '[' -> {
                if (canMoveBoxes3(newRobotPos, newRobotPos.offset(Direction.RIGHT), move)) {
                    moveBoxes2(newRobotPos, newRobotPos.offset(Direction.RIGHT), move)
                    robot = newRobotPos
                }
            }
            ']' -> {
                if (canMoveBoxes3(newRobotPos.offset(Direction.LEFT), newRobotPos, move)) {
                    moveBoxes2(newRobotPos.offset(Direction.LEFT), newRobotPos, move)
                    robot = newRobotPos
                }
            }
        }
//        printMap(map2)
    }

    printMap(map2)

    gpsSum = 0
    val boxList2 = map2.findAll('[')
    boxList2.forEach { gpsSum += 100 * it.r + it.c }

    print("GPS sum 2: $gpsSum")
}
