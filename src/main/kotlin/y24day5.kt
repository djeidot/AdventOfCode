fun y24day5() {
    val lines = readResource("y24day5.txt")

    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()

    var result = 0
    var result2 = 0

    var parseRules = true
    for (line in lines) {
        if (line.trim() == "") {
            parseRules = false
            continue
        }

        if (parseRules) {
            line.trim().split("|").let { rules.add(Pair(it[0].toInt(), it[1].toInt())) }
        } else {
            line.trim().split(",").let { updates.add(it.map { x -> x.toInt() })}
        }
    }

    for (update in updates) {
        var validUpdate = true
        for ((i, value) in update.withIndex()) {
            if (i == 0) {
                continue
            }

            val matchingRules = rules.filter { it.first == value && update.any { it2 -> it2 == it.second } }.map { it.second }
            for (match in matchingRules) {
                val j = update.indexOf(match)
                if (j != -1 && j < i) {
                    validUpdate = false
                    result2 += fixUpdate(update, rules)
                    break
                }
            }
            if (!validUpdate) {
                break
            }
        }
        if (validUpdate) {
            assert(update.count() % 2 == 1)
            val index = (update.count() - 1) / 2          // 0 1 _2_ 3 4,   (5 - 1) / 2 = 2
            result += update[index]
        }
    }

    println("result is $result")
    println("result2 is $result2")
}

fun fixUpdate(update: List<Int>, rules: MutableList<Pair<Int, Int>>): Int {
    val rulesComparator = Comparator<Int> { i1, i2 ->
        when {
            rules.firstOrNull { it.first == i1 && it.second == i2 } != null -> 1
            rules.firstOrNull { it.first == i2 && it.second == i1 } != null -> -1
            else -> 0
        }
    }

    val updateSorted = update.sortedWith(rulesComparator)
    return updateSorted[(updateSorted.count() - 1) / 2]
}
