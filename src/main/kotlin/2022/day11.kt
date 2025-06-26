package `2022`

import readResource

class Monkey() {
    val items = mutableListOf<Long>()
    lateinit var operation: (Long) -> Long
    lateinit var test: (Long) -> Boolean
    var monkeyTrue = 0
    var monkeyFalse = 0
    var inspections = 0L

    fun addItem(item: Long) {
        items.add(item)
    }

    fun doRound() {
        for (item in items) {
            var newItem = operation(item)
            inspections++
            newItem %= 9699690L
            val newMonkeyIndex = if (test(newItem)) monkeyTrue else monkeyFalse
            allMonkeys[newMonkeyIndex].addItem(newItem)
        }
        items.clear()
    }
}

val allMonkeys = mutableListOf<Monkey>()

fun day11() {
    fun getOperation(ops: List<String>): (Long) -> Long {
        val leftOp = if (ops[0] == "old") null else ops[0].toLong()
        val rightOp = if (ops[2] == "old") null else ops[2].toLong()

        return when(ops[1]) {
            "+" -> { old -> (leftOp ?: old) + (rightOp ?: old) }
            "*" -> { old -> (leftOp ?: old) * (rightOp ?: old) }
            else -> throw NotImplementedError()
        }
    }
    fun getTest(divisor: Long): (Long) -> Boolean {
        return { x -> x % divisor == 0L }
    }

    val lines = readResource("2022/day11.txt")
    for (i in lines.indices step 7) {
        val monkey = Monkey()
        val items = lines[i + 1].removePrefix("  Starting items: ").split(Regex(", ")).map { x -> x.toLong() }
        val operators = lines[i + 2].removePrefix("  Operation: new = ").split(" ")
        val testValue = lines[i + 3].removePrefix("  Test: divisible by ").toLong()
        val monkeyTrue = lines[i + 4].removePrefix("    If true: throw to monkey ").toInt()
        val monkeyFalse = lines[i + 5].removePrefix("    If false: throw to monkey ").toInt()

        monkey.items.addAll(items)
        monkey.operation = getOperation(operators)
        monkey.test = getTest(testValue)
        monkey.monkeyTrue = monkeyTrue
        monkey.monkeyFalse = monkeyFalse
        allMonkeys.add(monkey)
    }

    for (i in 1..10000) {
        for (monkey in allMonkeys) {
            monkey.doRound()
        }
    }

    val monkeyBusiness = allMonkeys.map { it.inspections }.sortedDescending().let { it[0] * it[1] }
    println("Monkey Business = $monkeyBusiness")
}