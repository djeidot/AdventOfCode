import kotlin.math.abs

class Trail(r: Int, c: Int) {

    val steps = List(10) { mutableSetOf<Position>() }

    init {
        steps[0].add(Position(r, c))
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

    fun tryDir(pos: Position, dir: Direction, id: Int): Boolean {
        val newPos = pos.offset(dir)
        if (newPos.r !in 0 until height || newPos.c !in 0 until width) {
            return false
        }
        return lines[newPos.r][newPos.c].digitToInt() == id
    }

    fun isConnected(pos1: Position, pos2: Position): Boolean =
        (abs(pos1.r - pos2.r) == 1 && pos1.c == pos2.c)
                || (abs(pos1.c - pos2.c) == 1 && pos1.r == pos2.r)

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

    fun getRating(trail: Trail, pos: Position, step: Int): Int {
        if (step == 0) {
            return 1
        }
        var rating = 0
        for (pos0 in trail.steps[step - 1]) {
            if (isConnected(pos, pos0)) {
                rating += getRating(trail, pos0,step - 1)
            }
        }
        return rating;
    }

    var rating = 0
    for (trail in trails) {
        for (pos in trail.steps[9]) {
            rating += getRating(trail, pos, 9)
        }
    }

    val totalScore = trails.sumOf { it.steps[9].count() }
    println("score is $totalScore")
    println("rating is $rating")
}