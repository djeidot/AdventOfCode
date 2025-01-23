fun y24day3() {
    val lines = readResource("y24day3.txt")

    var result = 0
    var doIt = true

    for (line in lines) {
        val values = Regex("""do\(\)|don't\(\)|mul\((\d+),(\d+)\)""").findAll(line)

        for (value in values) {
            when (value.value) {
                "do()" -> doIt = true
                "don't()" -> doIt = false
                else -> if (doIt) {
                    val mult = value.groups[1]!!.value.toInt() * value.groups[2]!!.value.toInt()
                    result += mult
                }
            }
        }
    }

    print("Result is $result")
}