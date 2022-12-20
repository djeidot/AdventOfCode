import java.lang.Integer.min
import java.lang.Math.max

fun day14() {
    val wall = mutableMapOf<Int, MutableSet<Int>>()

    fun addToWall(x: Int, y: Int) {
        val row = wall.getOrPut(x) { mutableSetOf() }   
        row.add(y)
    }
    
    fun isInWall(x: Int, y: Int) = wall[x]?.contains(y) ?: false

    // Add Rocks
    val lines = readResource("day14.txt")
    for (line in lines) {
        var prevX: Int? = null
        var prevY: Int? = null
        val coordList = line.split(" -> ")
        for (coord in coordList) {
            val coords = coord.split(",")
            val x = coords[0].toInt()
            val y = coords[1].toInt()
            if (prevX == null || prevY == null) {
                addToWall(x, y)
                prevX = x
                prevY = y
            } else if (prevX == x) {
                for (iy in min(prevY, y)..max(prevY, y)) {
                    addToWall(x, iy)
                }
                prevY = y
            } else if (prevY == y) {
                for (ix in min(prevX, x)..max(prevX, x)) {
                    addToWall(ix, y)
                }
                prevX = x
            } else {
                throw IllegalArgumentException("Don't know what to do here")
            }
        }
    }

    // Add floor
    val maxY = wall.flatMap { (_, y) -> y }.maxOf { it }
    val floorY = maxY + 2
    for (ix in 500 - floorY - 10..500 + floorY + 10) {
        addToWall(ix, floorY)
    }
    
    // Start dropping sand
    var sandCount = 0
    while (true) {
        var sandX = 500
        var sandY = 0

        var settled = false
        while (!settled) {
            if (!isInWall(sandX, sandY + 1)) {
                sandY++
            } else if (!isInWall(sandX - 1, sandY + 1)) {
                sandX--
                sandY++
            } else if (!isInWall(sandX + 1, sandY + 1)) {
                sandX++
                sandY++
            } else {
                addToWall(sandX, sandY)
                sandCount++
                if (sandX != 500 || sandY != 0) {
                    settled = true
                }
                break
            }
        }
        if (!settled) {
            break
        }
    }
    
    println("Sand count = $sandCount")
}