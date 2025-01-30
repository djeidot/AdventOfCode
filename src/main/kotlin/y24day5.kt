fun y24day5() {
    val lines = readResource("y24day5.txt")

    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()

    var result = 0

    var parseRules = true
    for (line in lines) {
        if (line.trim() == "") {
            parseRules = false
        }

        if (parseRules) {
            line.trim().split("|").let { rules.add(Pair(it[0].toInt(), it[1].toInt())) }
        } else {
            line.trim().split(",").let { updates.add(it.map { x -> x.toInt() })}
        }
    }

    for (update in updates) {
        for ((i, value) in update.withIndex()) {

        }
    }
}
