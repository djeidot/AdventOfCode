package `2022`

import readResource

fun day6() {
    fun getStartOfPacket(line: String, size: Int): Int {
        for (seg in line.windowed(size).withIndex()) {
            if (seg.value.toSet().count() == size) {
                return seg.index + size
            }
        }
        return 0
    }

    val lines = readResource("2022/day6.txt")
    println(getStartOfPacket(lines[0], 4))
    println(getStartOfPacket(lines[0], 14))
}