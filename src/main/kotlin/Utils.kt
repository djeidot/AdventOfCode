enum class Direction(val symbol: Char) {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>')
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

data class PositionXY(var x: Int, var y: Int)

class Matrix(val matrix: List<CharArray>) {
    constructor(width: Int, height: Int, fillChar: Char)
            : this(List<CharArray>(height) { CharArray(width) { fillChar } })

    fun getAt(row: Int, col: Int) = matrix[row][col]
    fun getAt(pos: PositionXY) = matrix[pos.y][pos.x]
    fun getAt(pos: Position) = matrix[pos.r][pos.c]
    fun setAt(pos: PositionXY, value: Char) { matrix[pos.y][pos.x] = value }
    fun setAt(pos: Position, value: Char) { matrix[pos.r][pos.c] = value }
    val height get() = matrix.count()
    val width get() = matrix[0].count()

    fun copy() = Matrix(matrix.map { it.copyOf() })

    fun findFirst(value: Char): Position {
        for (r in 0 until height) {
            for (c in 0 until width) {
                val pos = Position(r, c)
                if (getAt(pos) == value) {
                    return pos
                }
            }
        }
        throw IllegalStateException()
    }

    fun findAll(value: Char): List<Position> {
        val posList = mutableListOf<Position>()
        for (r in 0 until height) {
            for (c in 0 until width) {
                val pos = Position(r, c)
                if (getAt(pos) == value) {
                    posList.add(pos)
                }
            }
        }
        return posList
    }

    fun print() {
        for (r in 0 until height) {
            for (c in 0 until width) {
                print(getAt(Position(r, c)))
            }
            println()
        }
    }
}