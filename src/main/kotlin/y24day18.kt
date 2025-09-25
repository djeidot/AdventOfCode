import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun y24day18() {
    val lines = readResource("y24day18.txt")

    val map = Matrix(71, 71, '.')

    val numBytes = 1024
    val start = PositionXY(0, 0)
    val goal = PositionXY(70, 70)

    val allBytes = lines.map { PositionXY(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }

    for (i in 0 until numBytes) {
        map.setAt(allBytes[i], '#')
    }

    var optimalPath = mutableListOf<PositionXY>()

    fun manDist(pos1: PositionXY, pos2: PositionXY) = abs(pos1.x - pos2.x) + abs(pos1.y - pos2.y)
    fun eucDist(pos1: PositionXY, pos2: PositionXY) = sqrt(((pos1.x - pos2.x).toDouble()).pow(2) + ((pos1.y - pos2.y).toDouble()).pow(2))

    class AstarNode(val pos: PositionXY, parent: AstarNode?)
    {
        var parent: AstarNode? = null
        var distStart = Int.MAX_VALUE
        var distEnd = 0.0
        val fCost get() = distStart + distEnd

        init {
            if (parent != null) {
                update(parent)
            }
        }

        fun update(newParent: AstarNode) {
            this.parent = newParent
            this.distStart = newParent.distStart + manDist(pos, newParent.pos)
            this.distEnd = eucDist(pos, goal)
        }
    }

    fun neighbours(pos: PositionXY): List<PositionXY> {
        val neighbours = listOf(
            PositionXY(pos.x - 1, pos.y),
            PositionXY(pos.x + 1, pos.y),
            PositionXY(pos.x, pos.y - 1),
            PositionXY(pos.x, pos.y + 1),
        )
        return neighbours.filter {
            it.x in 0 until map.width && it.y in 0 until map.height && map.getAt(it) != '#'
        }
    }

    fun reconstructPath(node: AstarNode?): MutableList<PositionXY> {
        val path = mutableListOf<PositionXY>()
        var iNode = node
        while (iNode != null) {
            path.add(0, iNode.pos)
            iNode = iNode.parent
        }
        return path
    }

    fun printPath(path: MutableList<PositionXY>) {
        println("Min steps: ${path.size - 1}")
        val mapPrint = map.copy()
        for (pos in path) {
            mapPrint.setAt(pos, 'O')
        }
        mapPrint.print()
    }

    fun astar(): MutableList<PositionXY>? {
        val openList = mutableListOf(AstarNode(start, null))
        val closedList = mutableListOf<AstarNode>()
        while (openList.any()) {
            val current = openList.minByOrNull { it.fCost }!!

            if (current.pos == goal) {
                return reconstructPath(current)
            }

            closedList.add(current)
            openList.remove(current)

            for (neighbourPos in neighbours(current.pos)) {
                if (neighbourPos in closedList.map { it.pos }) {
                    continue
                }

                val tentative_g = current.distStart + manDist(current.pos, neighbourPos)
                val neighbour = openList.firstOrNull { it.pos == neighbourPos }
                if (neighbour == null) {
                    openList.add(AstarNode(neighbourPos, current))
                } else if (neighbour.distStart > tentative_g) {
                    neighbour.update(current)
                }
            }
        }
        return null
    }

    printPath(optimalPath)

    optimalPath = astar()!!
    printPath(optimalPath)

    for (i in numBytes until allBytes.size) {
        println("Adding byte $i - blocking position ${allBytes[i]}")
        map.setAt(allBytes[i], '#')
        if (astar() == null) {
            println("success!")
            break
        }
    }
}