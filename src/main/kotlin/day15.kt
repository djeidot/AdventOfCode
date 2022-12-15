import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

fun day15() {
    data class Position(val x: Int, val y: Int) {
        fun distanceFrom(other: Position) = abs(x - other.x) + abs(y - other.y)
    }

    val sensorsToBeacons = mutableMapOf<Position, Position>()
    val lines = readResource("day15.txt")
    for (line in lines) {
        val lineList = line.split(' ')
        val sensorX = lineList[2].removePrefix("x=").removeSuffix(",").toInt()
        val sensorY = lineList[3].removePrefix("y=").removeSuffix(":").toInt()
        val beaconX = lineList[8].removePrefix("x=").removeSuffix(",").toInt()
        val beaconY = lineList[9].removePrefix("y=").toInt()
        sensorsToBeacons[Position(sensorX, sensorY)] = Position(beaconX, beaconY)
    }

    fun getNonBeacons(targetY: Int): Set<Int> {
        val nonBeacons = mutableSetOf<Int>()

        for (sensor in sensorsToBeacons.keys) {
            val beacon = sensorsToBeacons[sensor]!!
            val distance = sensor.distanceFrom(beacon)
            if (targetY !in (sensor.y - distance)..(sensor.y + distance)) {
                continue
            }
            val distY = distance - abs(targetY - sensor.y)
            for (x in (sensor.x - distY)..(sensor.x + distY)) {
                nonBeacons += x
            }
            if (beacon.y == targetY) {
                nonBeacons -= beacon.x
            }
        }
        return nonBeacons
    }

    val nonBeacons = getNonBeacons(2000000)

    println("Non-beacons at 2000000: ${nonBeacons.count()}")

    data class IRange(val left: Int, val right: Int) {
        fun intersectWith(other: IRange): IRange? {
            if (other.left in left - 1..right - 1
                || other.right in left - 1..right - 1
                || left in other.left - 1..other.right - 1
                || right in other.left - 1..other.right - 1
            ) {
                return IRange(min(left, other.left), max(right, other.right))
            }
            return null
        }
    }

    val yRanges = Array(4000001) { mutableListOf<IRange>() }

    for (sensor in sensorsToBeacons.keys) {
        println("Processing sensor $sensor")
        val beacon = sensorsToBeacons[sensor]!!
        val distance = sensor.distanceFrom(beacon)
        for (y in 0..4000000) {
            if (y % 1000000 == 0) {
                println("Processing y=$y")
            }

            val distY = distance - abs(y - sensor.y)
            if (distY > 0) {
                yRanges[y].add(IRange(sensor.x - distY, sensor.x + distY))
            }
        }
    }

    println("Intersecting ranges")
    for (y in 0..4000000) {
        if (y % 1000000 == 0) {
            println("Processing y=$y")
        }

        val yRangeList = yRanges[y]
        var intersected = true
        while (intersected) {
            intersected = false
            yRangeList.sortBy { it.left }
            for (i in yRangeList.lastIndex - 1 downTo 0) {
                val newYRange = yRangeList[i].intersectWith(yRangeList[i + 1])
                if (newYRange != null) {
                    yRangeList[i] = newYRange
                    yRangeList.removeAt(i + 1)
                    intersected = true
                }
            }
        }
        if (yRangeList.size != 1) {
            println("Found distress signal at y=$y -> $yRangeList")
            val x = yRangeList[0].right + 1
            println("Tuning frequency for [$x, $y] = ${x * 4000000L + y}")
        }
    }
}