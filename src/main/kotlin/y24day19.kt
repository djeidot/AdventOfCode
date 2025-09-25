fun y24day19() {
    val lines = readResource("y24day19.txt")

    val patterns = lines[0].split(",").map { it.trim() }
    val designs = lines.drop(2).map { it.trim() }

    fun checkDesign(design: String): Boolean {
        return design.isEmpty() || patterns.any { design.startsWith(it) && checkDesign(design.drop(it.length)) }
    }

    val designPatterns = mutableMapOf<String, Long>()

    fun checkDesignSum(design: String): Long {
        if (design.isEmpty()) {
            return 1
        }
        if (design in designPatterns) {
            return designPatterns.getOrDefault(design, 0)
        }
        val validPatterns = patterns.filter { design.startsWith(it) }
        println("design: $design, ${validPatterns.size} valid patterns")
        var sum = 0L
        for (pattern in validPatterns) {
            sum += checkDesignSum(design.drop(pattern.length))
        }
        designPatterns[design] = sum
        return sum
    }

//    var countValid = 0
//    var countInvalid = 0
//    for (design in designs) {
//        if (checkDesign(design)) {
//            countValid++
//        } else {
//            countInvalid++
//        }
//    }
//    println("Valid designs: $countValid")
//    println("Invalid designs: $countInvalid")

    var sum = 0L
    for ((idx, design) in designs.withIndex()) {
        sum += checkDesignSum(design)
        println("$idx - sum at $sum")
    }
}