import `2022`.PositionXY

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

data class Position(val r: Int, val c: Int) {
    fun offset(dir: Direction): Position {
        val newR = r + (if (dir == Direction.DOWN) 1 else 0) + (if (dir == Direction.UP) -1 else 0)
        val newC = c + (if (dir == Direction.RIGHT) 1 else 0) + (if (dir == Direction.LEFT) -1 else 0)
        return Position(newR, newC)
    }

    override fun toString(): String {
        return "{$r,$c}"
    }
}

class Matrix(val matrix: List<CharArray>) {
    fun getAt(row: Int, col: Int) = matrix[row][col]
    fun getAt(pos: PositionXY) = matrix[pos.y][pos.x]
    fun getAt(pos: Position) = matrix[pos.r][pos.c]
    fun setAt(pos: PositionXY, value: Char) { matrix[pos.y][pos.x] = value }
    fun setAt(pos: Position, value: Char) { matrix[pos.r][pos.c] = value }
    val height get() = matrix.count()
    val width get() = matrix[0].count()

    fun copy() = Matrix(matrix.map { it.copyOf() })
}