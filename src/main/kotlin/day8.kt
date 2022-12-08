fun day8() {
    val lines = readResource("day8.txt");

    fun isVisible(row: Int, column: Int): Boolean {
        val line = lines[row]
        val cell = lines[row][column].digitToInt()
        return line.subSequence(0, column).all { it.digitToInt() < cell } ||
            line.subSequence(column + 1, line.length).all { it.digitToInt() < cell } ||
            lines.subList(0, row).all { it[column].digitToInt() < cell } ||
            lines.subList(row + 1, lines.size).all { it[column].digitToInt() < cell }
    }

    fun scenicScore(row: Int, column: Int): Int {
        fun <T> List<T>.treesViewed(cell: Int, op: (T) -> Int): Int {
            return this.indexOfFirst { op(it) >= cell }.let { if (it == -1) this.lastIndex else it } + 1
        }
        
        val line = lines[row]
        val cell = lines[row][column].digitToInt()
        val left = line.subSequence(0, column).reversed().toList().treesViewed(cell) { it.digitToInt() }
        val right = line.subSequence(column + 1, line.length).toList().treesViewed(cell) { it.digitToInt() }
        val up = lines.subList(0, row).reversed().treesViewed(cell) { it[column].digitToInt() }
        val down = lines.subList(row + 1, lines.size).treesViewed(cell) { it[column].digitToInt() }
        return left * right * up * down
    }
    
    val allVisible = lines.foldIndexed(0) { row, sum, line ->
        sum + line.foldIndexed(0) { col, count, _ ->
            count + if (isVisible(row, col)) 1 else 0 
        } 
    }
    
    val maxScenicScore = lines.foldIndexed(0) { row, maxByRow, line ->
        maxOf(maxByRow, line.foldIndexed(0) { col, maxByColumn, _ ->
            maxOf(maxByColumn, scenicScore(row, col))
        })
    }
    
    println("All visible: $allVisible")
    println("Max Scenic Score: $maxScenicScore")
}