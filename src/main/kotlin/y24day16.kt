import kotlin.math.abs

fun y24day16() {
    val lines = readResource("y24day16.txt")
    val map = Matrix(lines.map { it.toCharArray() })

    class PathItem(val pos: Position, val dir: Direction, val score: Int) {
        override fun toString(): String {
            return "$pos, $dir, $score"
        }
    }

    val start = map.findFirst('S')
    val end = map.findFirst('E')

    val allPaths = mutableListOf<MutableList<PathItem>>()
    allPaths.add(mutableListOf(PathItem(start, Direction.RIGHT, 0)))

    var completedPaths = mutableListOf<List<PathItem>>()

    var iter = 0
    while (allPaths.any()) {
        iter++
        for ((index, path) in allPaths.withIndex().reversed()) {
            val lastItem = path.last()
            val validDirs = mutableListOf<Direction>()
            for (dir in Direction.values()) {
                val nextPos = lastItem.pos.offset(dir)
                val nextVal = map.getAt(nextPos)
                if (nextVal == 'E') {
                    validDirs.clear()
                    validDirs.add(dir)
                    break
                } else if (nextVal == '#') {
                    continue
                } else if (path.any { it.pos == nextPos }) {
                    continue
                } else {
                    validDirs.add(dir)
                }
            }
            if (validDirs.isEmpty()) {
                // No way to go - not a valid path, delete from map
                allPaths.removeAt(index)
            }
            for ((indexDir, dir) in validDirs.withIndex().reversed()) {
                val nextPos = lastItem.pos.offset(dir)
                val nextScore = lastItem.score + if (lastItem.dir == dir) 1 else 1001
                val nextItem = PathItem(nextPos, dir, nextScore)

                if (indexDir >= 1) {
                    // copy path to a new one
                    val path1 = path.toMutableList()
                    path1.add(nextItem)
                    allPaths.add(path1)
                } else {
                    path.add(nextItem)

                    if (map.getAt(nextPos) == 'E') {
                        // finished - move path from allPaths to completedPaths
                        completedPaths.add(path)
                        allPaths.removeAt(index)
                    }
                }
            }
        }

        var removeCount = 0
        // cleanup paths
        for (path in allPaths.toMutableList()) {
            // check score of last position, see if other paths have the same position with the same or lower score
            val lastPathItem = path.last()
            if (allPaths.filterNot { it == path }.any {
                    it.any { item ->
                        item.pos == lastPathItem.pos && item.dir == lastPathItem.dir && item.score < lastPathItem.score
                    }
                } || completedPaths.any {
                    it.any { item ->
                        item.pos == lastPathItem.pos && item.dir == lastPathItem.dir && item.score < lastPathItem.score
                    }
                } || completedPaths.any {
                    it.last().score < lastPathItem.score
                }) {
                allPaths.remove(path)
                removeCount++
            }
        }

        val minDistance = if (allPaths.isEmpty()) 0 else allPaths.map { it.last() }.minOf { abs(end.c - it.pos.c) + abs(end.r - it.pos.r) }
//        println()
//        allPaths.forEach { println(it) }
        println("It: $iter, Paths: ${allPaths.count()}, Removed: $removeCount, Completed: ${completedPaths.count()}, Min Distance: $minDistance")
    }

    val minScore = completedPaths.minOf { it.last().score }
    val minPaths = completedPaths.filter { it.last().score == minScore }

    val pathTiles = mutableSetOf<Position>();
    for (minPath in minPaths) {
        minPath.forEach { pathTiles.add(it.pos) }
    }

    println("Minimum score is $minScore")
    println("Number of min paths: ${minPaths.count()}")
    println("Number of tiles: ${pathTiles.count()}")
}