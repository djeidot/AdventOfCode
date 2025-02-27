import java.math.BigDecimal
import kotlin.math.pow

fun y24day7() {
    val lines = readResource("y24day7.txt")

    var total = BigDecimal(0)

    for ((index, line) in lines.withIndex()) {
        println("line $index of ${lines.count()}")
        val (test, operands) = line.split(":").let { Pair(it[0].toBigDecimal(), it[1].trim().split(" ").map { x -> x.toBigDecimal() }) }
        val operator_sets = 3.0.pow(operands.count() - 1).toInt()

        for (operator_set in 0 until operator_sets) {
            var result = operands[0]
            for (i in 1..operands.lastIndex) {
                val operator = (operator_set / (3.0.pow(i - 1).toInt())) % 3

                when (operator) {
                    0 -> result *= operands[i]
                    1 -> result += operands[i]
                    2 -> result = "$result${operands[i]}".toBigDecimal()
                }
                if (result > test) {
                    break
                }
            }
            if (result == test) {
                total += result
                break
            }
        }
    }

    print("total is $total")
}