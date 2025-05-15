

data class PositionRC(val r: Int, val c: Int) {
    fun offset(dir: Direction): PositionRC {
        val newR = r + (if (dir == Direction.DOWN) 1 else 0) + (if (dir == Direction.UP) -1 else 0)
        val newC = c + (if (dir == Direction.RIGHT) 1 else 0) + (if (dir == Direction.LEFT) -1 else 0)
        return PositionRC(newR, newC)
    }
}

class Trail(r: Int, c: Int) {

    val steps = List(10) { mutableSetOf<PositionRC>() }

    init {
        steps[0].add(PositionRC(r, c))
    }
}

fun y24day10() {
    val lines = readResource("y24day10.txt")
    val height = lines.count()
    val width = lines[0].count()

    val trails = mutableListOf<Trail>()

    for (r in 0 until height) {
        for (c in 0 until width) {
            if (lines[r][c] == '0') {
                trails.add(Trail(r, c))
            }
        }
    }

    fun tryDir(pos: PositionRC, dir: Direction, id: Int): Boolean {
        val newPos = pos.offset(dir)
        if (newPos.r !in 0 until height || newPos.c !in 0 until width) {
            return false
        }
        return lines[newPos.r][newPos.c].digitToInt() == id
    }

    for (trail in trails) {
        for (step in 0..8) {
            for (pos in trail.steps[step]) {
                for (dir in Direction.values()) {
                    if (tryDir(pos, dir, step + 1)) {
                        trail.steps[step + 1].add(pos.offset(dir))
                    }
                }
            }
        }
    }

    val totalScore = trails.sumOf { it.steps[9].count() }
    println("score is $totalScore")
}