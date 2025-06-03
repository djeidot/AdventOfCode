class Region(firstPos: Position) {
    val positions = mutableListOf<Position>()

    init {
        positions.add(firstPos)
    }

    override fun toString(): String {
        return positions.toString()
    }

    val area get() = positions.count()
    val perimeter get() =
        positions.sumOf { pos -> Direction.values().count { dir -> pos.offset(dir) !in positions } }
    val sides get() = countSides(Direction.UP, Direction.RIGHT) +
                countSides(Direction.RIGHT, Direction.DOWN) +
                countSides(Direction.DOWN, Direction.LEFT) +
                countSides(Direction.LEFT, Direction.UP)

    private fun countSides(dirFace: Direction, dirBuddy: Direction): Int {
        val facePositions = positions.filter { it.offset(dirFace) !in positions }
        val noBuddyPositions = facePositions.filter { it.offset(dirBuddy) !in facePositions }
        return noBuddyPositions.count()
    }
}

fun y24day12() {
    val lines = readResource("y24day12.txt")

    fun getAt(pos: Position) = lines[pos.r][pos.c]

    val regions = mutableMapOf<Char, MutableList<Region>>()

    // fill out regions
    for (r in 0 until lines.count()) {
        for (c in 0 until lines[r].count()) {
            val letter = lines[r][c]
            val pos = Position(r, c)
            if (letter in regions.keys) {
                val regionList = regions[letter]
                var foundAdjacent = false
                for (innerRegion in regionList!!) {
                    if (hasAdjacents(pos, innerRegion)) {
                        innerRegion.positions.add(pos)
                        foundAdjacent = true
                        break
                    }
                }
                if (!foundAdjacent) {
                    regionList.add(Region(pos))
                }
            } else {
                regions[letter]= mutableListOf(Region(pos))
            }
        }
    }

    // need to try to merge regions since filling them out by position might not work every time
    for (regionList in regions.values) {
        if (regionList.count() == 1) {
            continue
        }
        for (i in 0 until regionList.lastIndex) {
            var j = i
            while (++j < regionList.count()) {
                for (pos in regionList[j].positions) {
                    if (hasAdjacents(pos, regionList[i])) {
                        // merge regions
                        regionList[i].positions += regionList[j].positions
                        regionList.removeAt(j)
                        j = i
                        break
                    }
                }
            }
        }
    }

    val totalArea = regions.values.sumOf { rl -> rl.sumOf { r -> r.area * r.perimeter } }
    println("total perimeter area is $totalArea")
    val totalSideArea = regions.values.sumOf { rl -> rl.sumOf { r -> r.area * r.sides } }
    println("total side area is $totalSideArea")
}

fun hasAdjacents(pos: Position, region: Region): Boolean = region.positions.any { isAdjacent(pos, it) }
fun isAdjacent(pos: Position, otherPos: Position): Boolean = Direction.values().any { pos.offset(it) == otherPos }

