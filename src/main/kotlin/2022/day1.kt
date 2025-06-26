package `2022`

import readResource

fun day1() {
    val lines = readResource("2022/day1.txt")
    val elfCalories = mutableListOf<Int>()
    var elf = 0
    lines.forEach { line ->
        if (line.isEmpty()) {
            elfCalories.add(elf)
            elf = 0
            return@forEach
        }
        elf += line.toInt()
    }
    println("Max elf calories is ${elfCalories.maxOrNull()}")
    println("Top three elf calories are: ${elfCalories.sorted().takeLast(3)}")
    println("Top three elf calories sum is: ${elfCalories.sorted().takeLast(3).sum()}")
}