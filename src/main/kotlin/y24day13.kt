data class ButtonData (val ax: Int, val ay: Int, val bx: Int, val by: Int, val cx: Long, val cy: Long) {

    val aTimes get() = (by * cx - bx * cy) / (ax * by - ay * bx).toDouble()
    val bTimes get() = (ax * cy - ay * cx) / (ax * by - ay * bx).toDouble()
}

fun y24day13() {
    val lines = readResource("y24day13.txt")

    val machines = mutableListOf<ButtonData>();

    for (i in 0..lines.lastIndex step 4) {
        val (ax, ay) = Regex("Button A: X([0-9+-]+), Y([0-9+-]+)").find(lines[i])!!.destructured
        val (bx, by) = Regex("Button B: X([0-9+-]+), Y([0-9+-]+)").find(lines[i + 1])!!.destructured
        val (cx, cy) = Regex("Prize: X=([0-9-]+), Y=([0-9-]+)").find(lines[i + 2])!!.destructured

        machines.add(ButtonData(
            ax.toInt(), ay.toInt(),
            bx.toInt(), by.toInt(),
            cx.toLong() + 10000000000000L, cy.toLong() + 10000000000000L
        ))
    }

    var tokens = 0L

    for (machine in machines) {
        val aTimes = machine.aTimes
        val bTimes = machine.bTimes

        if (aTimes % 1.0 == 0.0 && bTimes % 1.0 == 0.0) {
            tokens += aTimes.toLong() * 3 + bTimes.toLong()
        }
    }

    println("Tokens = $tokens")
}