fun y24day4() {
    val lines = readResource("y24day4.txt")

    val height = lines.count()
    val width = lines[0].count()

    var xmasCount = 0
    var masCount = 0
    for ((row, line) in lines.withIndex()) {
        for ((col, char) in line.withIndex()) {
            if (char != 'X') {
                continue
            }

            for (direction in Directions.values()) {
                if (row + direction.y * 3 !in 0 until height
                    || col + direction.x * 3 !in 0 until width)
                    continue

                val cursor = Cursor(col, row)

                if (!checkNext(lines, cursor, direction, 'M'))
                    continue

                if (!checkNext(lines, cursor, direction, 'A'))
                    continue

                if (!checkNext(lines, cursor, direction, 'S'))
                    continue

                xmasCount++
            }
        }
        for ((col, char) in line.withIndex()) {
            if (char != 'A') {
                continue
            }

            if (row !in 1 until height - 1 || col !in 1 until width - 1) {
                continue
            }

            val NW = lines[row - 1][col - 1]
            val NE = lines[row - 1][col + 1]
            val SW = lines[row + 1][col - 1]
            val SE = lines[row + 1][col + 1]

            when (NW) {
                'M' -> if (SE != 'S') continue
                'S' -> if (SE != 'M') continue
                else -> continue
            }
            when (NE) {
                'M' -> if (SW != 'S') continue
                'S' -> if (SW != 'M') continue
                else -> continue
            }

            masCount++
        }
    }

    println("xmas count is $xmasCount")
    println("x-mas count is $masCount")
}

class Cursor(var x: Int, var y: Int)

fun checkNext(lines: List<String>, cursor: Cursor, direction: Directions, charToCheck: Char): Boolean {
    cursor.x += direction.x
    cursor.y += direction.y

    return lines[cursor.y][cursor.x] == charToCheck
}

enum class Directions(val x: Int, val y: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);
}

