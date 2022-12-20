fun main() {
    day14()
}

// helpers
fun readResource(fileName: String) =
    {}::class.java.getResourceAsStream(fileName)?.bufferedReader()?.readLines() ?: listOf("")