package `2022`

import readResource

fun day3() {
    fun priority(item: Char) = when (item) {
        in ('a'..'z') -> item.code - 97 + 1
        in ('A'..'Z') -> item.code - 65 + 27
        else -> throw IllegalArgumentException()
    }

    val lines = readResource("2022/day3.txt")
    var prioritySum = 0
    for (line in lines) {
        assert(line.length % 2 != 0)

        val lineLeft = line.subSequence(0 until line.length / 2).toList()
        val lineRight = line.subSequence(line.length / 2 until line.length).toList()
        assert(lineLeft.size == lineRight.size)

        val lineCommon = lineLeft.filter { it in lineRight }
        assert(lineCommon.size <= 1)

        if (lineCommon.isNotEmpty()) {
            prioritySum += priority(lineCommon[0])
        }
    }
    println("priority sum (1): $prioritySum")
    prioritySum = 0
    for (lineIndex in 0..lines.lastIndex step 3) {
        val rs1 = lines[lineIndex]
        val rs2 = lines[lineIndex + 1]
        val rs3 = lines[lineIndex + 2]

        val common = rs1.filter{ it in rs2 }.filter{ it in rs3 }.toList()
        assert(common.size == 1)

        prioritySum += priority(common[0])
    }
    println("priority sum (2): $prioritySum")
}