fun day10() {
    var cycles = 0
    var register = 1
    var sumSignalStrengths = 0

    fun printPixel() {
        val position = (cycles - 1) % 40
        print(if (position in (register - 1)..(register + 1)) '#' else '.')
        if (position == 39) {
            println()
        }
    }
    fun cycle() {
        cycles++
        printPixel()
        if (cycles in listOf(20, 60, 100, 140, 180, 220)) {
            sumSignalStrengths += register * cycles
        }
    }
    fun noop() {
        cycle()
    }
    fun addx(value: Int) {
        cycle()
        cycle()
        register += value
    }

    println("1234567890123456789012345678901234567890")
    val lines = readResource("day10.txt")
    for (line in lines) {
        val lineList = line.split(' ')
        when (lineList[0]) {
            "noop" -> noop()
            "addx" -> addx(lineList[1].toInt())
        }
    }

    println("Sum of signal strengths: $sumSignalStrengths")
}