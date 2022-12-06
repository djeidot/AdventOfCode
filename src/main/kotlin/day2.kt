enum class RPS {
    rock,
    paper,
    scissors
}
fun day2() {
    val rpsScores = mapOf(RPS.rock to 1, RPS.paper to 2, RPS.scissors to 3)
    val gameScores = mapOf(
        RPS.rock to mapOf(RPS.rock to 3, RPS.paper to 0, RPS.scissors to 6),
        RPS.paper to mapOf(RPS.rock to 6, RPS.paper to 3, RPS.scissors to 0),
        RPS.scissors to mapOf(RPS.rock to 0, RPS.paper to 6, RPS.scissors to 3)
    )
    val oppoMap = mapOf('A' to RPS.rock, 'B' to RPS.paper, 'C' to RPS.scissors)

    //val mineMap = mapOf('X' to RPS.rock, 'Y' to RPS.paper, 'Z' to RPS.scissors)
    fun getDefeat(oppoRps: RPS, mineChar: Char): RPS {
        return when (mineChar) {
            'X' -> {    // oppo needs to win
                gameScores[oppoRps]!!.filter { it.value == 6 }.keys.first()
            }
            'Y' -> {    // need to draw
                gameScores[oppoRps]!!.filter { it.value == 3 }.keys.first()
            }
            'Z' -> {    // oppo needs to lose
                gameScores[oppoRps]!!.filter { it.value == 0 }.keys.first()
            }
            else -> throw IllegalArgumentException()
        }
    }

    val lines = readResource("day2.txt")
    var overallScore = 0
    for (line in lines) {
        val oppoChar = line[0]
        val mineChar = line[2]
        val oppoRps = oppoMap[oppoChar]!!
        //val mineRps = mineMap[mineChar]!!
        val mineRps = getDefeat(oppoRps, mineChar)
        val score = rpsScores[mineRps]!! + gameScores[mineRps]!![oppoRps]!!
        overallScore += score
    }
    print("Overall Score = $overallScore")
}