fun main() {
    y24day17()
}

// helpers
fun readResource(fileName: String) =
    {}::class.java.getResourceAsStream(fileName)?.bufferedReader()?.readLines() ?: listOf("")