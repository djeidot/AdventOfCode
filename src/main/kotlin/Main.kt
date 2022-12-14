fun main() {
    day12()
}

// helpers
fun readResource(fileName: String) =
    {}::class.java.getResourceAsStream(fileName)?.bufferedReader()?.readLines() ?: listOf("")