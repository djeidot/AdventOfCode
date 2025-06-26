package `2022`

import readResource

fun day8() {
    val lines = readResource("2022/day8.txt");

    fun isVisible(row: Int, column: Int): Boolean {
        val line = lines[row]
        val cell = lines[row][column]
        return line.subSequence(0, column).all { it < cell } ||
            line.subSequence(column + 1, line.length).all { it < cell } ||
            lines.subList(0, row).all { it[column] < cell } ||
            lines.subList(row + 1, lines.size).all { it[column] < cell }
    }

    fun scenicScore(row: Int, column: Int): Int {
        fun <T> List<T>.treesViewed(cell: Char, op: (T) -> Char) =
            indexOfFirst { op(it) >= cell }.let { if (it == -1) this.lastIndex else it } + 1
        
        val line = lines[row]
        val cell = lines[row][column]
        val left = line.subSequence(0, column).reversed().toList().treesViewed(cell) { it }
        val right = line.subSequence(column + 1, line.length).toList().treesViewed(cell) { it }
        val up = lines.subList(0, row).reversed().treesViewed(cell) { it[column] }
        val down = lines.subList(row + 1, lines.size).treesViewed(cell) { it[column] }
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