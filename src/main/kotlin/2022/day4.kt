package `2022`

import readResource

fun day4() {
    infix fun IntRange.isIn(other: IntRange): Boolean =
        other.contains(this.first) && other.contains(this.last)
    infix fun IntRange.overlaps(other: IntRange): Boolean =
        this.any { other.contains(it) }

    val lines = readResource("2022/day4.txt")
    var fullyContainedAssignmentPairs = 0
    var overlappingPairs = 0
    for (line in lines) {
        val regions = line.split(",")
        val elf1Values = regions[0].split("-")
        val elf2Values = regions[1].split("-")
        val elf1Range = elf1Values[0].toInt()..elf1Values[1].toInt()
        val elf2Range = elf2Values[0].toInt()..elf2Values[1].toInt()

        if (elf1Range isIn elf2Range || elf2Range isIn elf1Range) {
            fullyContainedAssignmentPairs++
        }
        if (elf1Range overlaps elf2Range) {
            overlappingPairs++
        }
    }
    println("Fully contained assignment pairs: $fullyContainedAssignmentPairs")
    println("Overlapping pairs: $overlappingPairs")
}