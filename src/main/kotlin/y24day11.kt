
fun y24day11() {
    val lines = readResource("y24day11.txt")

    var stones = mutableMapOf<String, Long>()

    val iterations = 75

    for (stone in lines[0].split(" ")) {
        stones[stone] = 1
    }

    for (it in 1..iterations) {
        println("it: $it, stone count: ${stones.values.sum()}")
        val newStones = mutableMapOf<String, Long>()
        for ((stone, count) in stones) {
            if (stone == "0") {
                newStones["1"] = (newStones["1"] ?: 0) + count
                continue
            }
            if (stone.length % 2 == 0) {
                val stone1 = stone.take(stone.length / 2).trimStart('0').ifEmpty { "0" }
                val stone2 = stone.takeLast(stone.length / 2).trimStart('0').ifEmpty { "0" }
                newStones[stone1] = (newStones[stone1] ?: 0) + count
                newStones[stone2] = (newStones[stone2] ?: 0) + count
                continue
            }
            val stone3 = (stone.toLong() * 2024L).toString()
            newStones[stone3] = (newStones[stone3] ?: 0) + count
        }
        stones = newStones
    }

    print("end stones: ${stones.values.sum()}")
}