enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

data class PositionRC(val r: Int, val c: Int) {
    fun offset(dir: Direction): PositionRC {
        val newR = r + (if (dir == Direction.DOWN) 1 else 0) + (if (dir == Direction.UP) -1 else 0)
        val newC = c + (if (dir == Direction.RIGHT) 1 else 0) + (if (dir == Direction.LEFT) -1 else 0)
        return PositionRC(newR, newC)
    }

    override fun toString(): String {
        return "{$r,$c}"
    }
}