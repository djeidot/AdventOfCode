import kotlin.math.abs
import kotlin.math.sign

fun y24day2() {
    val lines = readResource("y24day2.txt")

    var safeCount = 0

    for (line in lines) {
        val values = line.split(' ').map { it.toInt() }
        assert(values.count() > 1)

        if (isSafe(values, true)) {
            safeCount++
        }
    }

    print("Number of safe reports: $safeCount")
}

fun isSafe(values: List<Int>, recurse: Boolean): Boolean {
    val sign = (values[1] - values[0]).sign

    for (i in 0 until values.size - 1) {
        val diff = values[i + 1] - values[i]
        if (diff.sign != sign || abs(diff) !in 1..3) {
            if (recurse) {
                for (j in values.indices) {
                    val valuesSans = values.toMutableList()
                    valuesSans.removeAt(j)
                    if (isSafe(valuesSans.toList(), false)) {
                        return true
                    }
                }
            }
            return false
        }
    }
    return true
}