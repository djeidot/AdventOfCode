fun main() {
    y24day15()
}

// helpers
fun readResource(fileName: String) =
    {}::class.java.getResourceAsStream(fileName)?.bufferedReader()?.readLines() ?: listOf("")