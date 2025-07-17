data class Robot(var px: Int, var py: Int, val vx: Int, val vy: Int)

fun y24day14() {
    val lines = readResource("y24day14.txt")

    val width = 101
    val height = 103
    val iterations = 7510

    val robots = mutableListOf<Robot>()
    for (line in lines) {
        val (px, py, vx, vy) = Regex("p=([0-9-]+),([0-9-]+) v=([0-9-]+),([0-9-]+)").find(line)!!.destructured
        robots.add(Robot(px.toInt(), py.toInt(), vx.toInt(), vy.toInt()))
    }

    fun printLayout(robots: List<Robot>, iteration: Int) {
        println("Iteration $iteration")
        for (iy in 0 until height) {
            for (ix in 0 until width) {
                val robots = robots.count { it.px == ix && it.py == iy }
                print(if (robots == 0) " " else "*" );
            }
            println()
        }
        println()
    }

    for (iteration in 1..iterations) {
        for (robot in robots) {
            robot.px = (robot.px + robot.vx + width) % width
            robot.py = (robot.py + robot.vy + height) % height
        }

        if (iteration == 7502) {
            printLayout(robots, iteration)
        }
    }

    val range1X = 0 until (if (width % 2 == 0) width else width - 1) / 2
    val range2X = (if (width % 2 == 0) width else width + 1) / 2 until width
    val range1Y = 0 until (if (height % 2 == 0) height else height - 1) / 2
    val range2Y = (if (height % 2 == 0) height else height + 1) / 2 until height

    val quad1 = robots.count { it.px in range1X && it.py in range1Y }.toLong()
    val quad2 = robots.count { it.px in range2X && it.py in range1Y }.toLong()
    val quad3 = robots.count { it.px in range1X && it.py in range2Y }.toLong()
    val quad4 = robots.count { it.px in range2X && it.py in range2Y }.toLong()

    val safetyFactor = quad1 * quad2 * quad3 * quad4

    println ("Safety Factor: $safetyFactor")
}