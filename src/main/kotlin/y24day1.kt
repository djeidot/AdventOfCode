import kotlin.math.abs

fun y24day1() {
    val lines = readResource("y24day1.txt")
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    for (line in lines) {
        val numbers = line.split(Regex("\\s+"))
        assert(numbers.size == 2)
        list1.add(numbers[0].toInt())
        list2.add(numbers[1].toInt())
    }

    list1.sort()
    list2.sort()

    var distance = 0

    for (i in list1.indices) {
        distance += abs(list1[i] - list2[i])
    }

    println("total distance sum: ${distance}")

    var similarity = 0

    for (list1Number in list1) {
        val count = list2.count { it == list1Number }
        similarity += list1Number * count
    }

    println("similarity score: ${similarity}")
}