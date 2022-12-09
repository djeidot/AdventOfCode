data class Position(var x: Int, var y: Int)


fun day9() {
    val head = Position(0, 0)
    val tailSize = 1
    val tails = List(tailSize) { Position(0, 0) }
    val tailPositions = mutableSetOf(tails.last().copy())

    fun moveTail(front: Position, back: Position) {
        val diffX = front.x - back.x
        val diffY = front.y - back.y

        if (diffX > 1 || (diffX == 1 && diffY !in -1..1)) { back.x++ }
        if (diffX < -1 || (diffX == -1 && diffY !in -1..1)) { back.x-- }
        if (diffY > 1 || (diffY == 1 && diffX !in -1..1)) { back.y++ }
        if (diffY < -1 || (diffY == -1 && diffX !in -1..1)) { back.y-- }
    }

    fun moveHead(direction: Char, steps: Int) {
        for (step in 1..steps) {
            when (direction) {
                'U' -> head.x++
                'D' -> head.x--
                'L' -> head.y--
                'R' -> head.y++
            }
            moveTail(head, tails.first())
            for (i in 0 until tails.lastIndex) {
                moveTail(tails[i], tails[i + 1])
            }
            tailPositions.add(tails.last().copy())
            println("Head: $head, Tails: $tails, TailPositions: ${tailPositions.size}")
        }
    }

    val lines = readResource("day9.txt")
    for (line in lines) {
        val lineList = line.split(' ')
        moveHead(lineList[0][0], lineList[1].toInt())
    }

    println("Number of unique tail positions: ${tailPositions.size}")
}
