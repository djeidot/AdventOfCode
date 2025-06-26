package `2022`

import readResource

fun day5() {
    val lines = readResource("2022/day5.txt")
    val stackBaseLine = lines.indexOfFirst { it[1] == '1' }
    val numberOfStacks = lines[stackBaseLine].last().digitToInt()
    val stacks = List<MutableList<Char>>(numberOfStacks) { mutableListOf() }
    for (i in 0 until stackBaseLine) {
        val line = lines[i]
        for (stack in 1..numberOfStacks) {
            val charPosition = (stack - 1) * 4 + 1
            if (line.length > charPosition && line[charPosition] != ' ') {
                stacks[stack - 1].add(0, line[charPosition])
            }
        }
    }
    println(stacks)

    fun move1(howMany: Int, from: Int, to: Int) {
        for (i in 0 until howMany) {
            val crate = stacks[from - 1].removeLast()
            stacks[to - 1].add(crate)
        }
    }

    fun move2(howMany: Int, from: Int, to: Int) {
        val crates = stacks[from - 1].takeLast(howMany)
        for (i in 0 until howMany) {
            stacks[from - 1].removeLast()
        }
        stacks[to - 1].addAll(crates)
    }

    for (i in stackBaseLine + 2..lines.lastIndex) {
        val line = lines[i]
        val instruction = line.split(Regex("move|from|to"))
        val howMany = instruction[1].trim().toInt()
        val from = instruction[2].trim().toInt()
        val to = instruction[3].trim().toInt()
        //move1(howMany, from, to)
        move2(howMany, from, to)
    }

    stacks.forEach { print(it.last()) }
}