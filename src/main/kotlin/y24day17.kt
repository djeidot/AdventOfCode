fun y24day17() {
    val lines = readResource("y24day17.txt")

    var regA = 0L
    var regB = 0L
    var regC = 0L

    var program = lines[4].drop(8).trim().split(",").map { it.toInt() }
    var output= mutableListOf<Int>()

    fun combo(operand: Int): Long {
        return when (operand) {
            0, 1, 2, 3 -> operand.toLong()
            4 -> regA
            5 -> regB
            6 -> regC
            else -> throw IllegalArgumentException("combo operand set to $operand")
        }
    }

    fun adv(operand: Int): Int {
        if (combo(operand) > Int.MAX_VALUE) {
            throw IllegalStateException();
        }
        val num = regA
        val den = 1 shl combo(operand).toInt()
        regA = num / den
        return 2
    }

    fun bxl(operand: Int): Int {
        if (regB > Int.MAX_VALUE) {
            throw IllegalStateException();
        }
        regB = (regB.toInt() xor operand).toLong()
        return 2
    }

    fun bst(operand: Int): Int {
        regB = combo(operand) % 8
        return 2
    }

    fun jnz(operand: Int, pointer: Int): Int {
        return if (regA == 0L) pointer + 2 else operand
    }

    fun bxc(): Int {
        regB = regB xor regC
        return 2
    }

    fun out(operand: Int): Int {
        output.add((combo(operand) % 8).toInt())
        return 2
    }

    fun bdv(operand: Int): Int {
        if (combo(operand) > Int.MAX_VALUE) {
            throw IllegalStateException();
        }
        val num = regA
        val den = 1 shl combo(operand).toInt()
        regB = num / den
        return 2
    }

    fun cdv(operand: Int): Int {
        if (combo(operand) > Int.MAX_VALUE) {
            throw IllegalStateException();
        }
        val num = regA
        val den = 1 shl combo(operand).toInt()
        regC = num / den
        return 2
    }

    fun runProgram(size: Int): Boolean {
        var pointer = 0
        while (pointer < program.size - 1) {
            val instruction = program[pointer]
            val operand = program[pointer + 1]
            when (instruction) {
                0 -> pointer += adv(operand)
                1 -> pointer += bxl(operand)
                2 -> pointer += bst(operand)
                3 -> pointer = jnz(operand, pointer)
                4 -> pointer += bxc()
                5 -> pointer += out(operand)
                6 -> pointer += bdv(operand)
                7 -> pointer += cdv(operand)
                else -> throw IllegalArgumentException("instruction set to $instruction")
            }
            if (instruction == 5) {
//                println(output)
                val index = output.lastIndex
                if (output[index] != program[index + program.size - size])
                    return false
                if (output.size == size)
                    return true
            }
        }
        return false
    }

    var initRegA = 0L
    var size = 1
    regA = initRegA
    while (size <= 16) {
        while (!runProgram(size)) {
            output.clear()
            regA = ++initRegA
            regB = 0
            regC = 0
        }
        println("initRegA for size $size is $initRegA")
        size++
        initRegA *= 8L
    }
}