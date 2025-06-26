import `2022`.PositionXY

fun y24day8() {
    val antennas = mutableMapOf<Char, MutableList<PositionXY>>()
    val antinodes = mutableSetOf<PositionXY>()

    val lines = readResource("y24day8.txt")
    val height = lines.count()
    val width = lines[0].count()

    for ((y, line) in lines.withIndex()) {
        for ((x, char) in line.withIndex()) {
            if (char != '.') {
                if (char !in antennas.keys) {
                    antennas[char] = mutableListOf();
                }
                antennas[char]!!.add(PositionXY(x, y))
            }
        }
    }

    fun verifyAdd(pos: PositionXY): Boolean {
        if (pos.x in 0 until width && pos.y in 0 until height)
        {
            antinodes.add(pos)
            return true
        }
        return false
    }

    fun iterateAdd(pos: PositionXY, diff: PositionXY, forwards: Boolean) {
        if (!verifyAdd(pos)) return

        val newPos = if (forwards)
            PositionXY(pos.x + diff.x, pos.y + diff.y)
        else
            PositionXY(pos.x - diff.x, pos.y - diff.y)

        iterateAdd(newPos, diff, forwards)
    }

    for ((_, positions) in antennas) {
        for (i in positions.indices) {
            val pos1 = positions[i]
            for (j in i + 1..positions.lastIndex) {
                val pos2 = positions[j]
                val diff = PositionXY(pos2.x - pos1.x, pos2.y - pos1.y)

                iterateAdd(pos1, diff, false)
                iterateAdd(pos2, diff, true)
//                verifyAdd(Position(pos1.x - diff.x, pos1.y - diff.y))
//                verifyAdd(Position(pos2.x + diff.x, pos2.y + diff.y))
            }
        }
    }

    println("Number of antinodes: ${antinodes.count()}")
}