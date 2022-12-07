data class AcFile(val name: String, val size: Int)
data class AcDir(val name: String, val parent: AcDir?) {
    private val files = mutableListOf<AcFile>()
    private val subDirs = mutableListOf<AcDir>()
    
    fun addFile(file: AcFile) { files.add(file) }
    fun addDir(dirName: String): AcDir  { val dir = AcDir(dirName, this);  subDirs.add(dir); return dir }
    fun getDir(name: String) = subDirs.find { it.name == name }
    
    fun getSize(): Int = files.sumOf { it.size } + subDirs.sumOf { it.getSize() }
}

fun day7() {
    val lines = readResource("day7.txt")
    val baseDir = AcDir("/", null)
    val allDirs = mutableListOf<AcDir>()
    var curDir = baseDir
    for (line in lines) {
        val args = line.split(' ')
        when (args[0]) {
            "$" -> when (args[1]) {
                "cd" -> curDir = when (args[2]) {
                    "/" -> baseDir
                    ".." -> curDir.parent!!
                    else -> curDir.getDir(args[2])!!
                }
                "ls" -> {}
                else -> throw IllegalArgumentException("unrecognized command $line")
            }
            "dir" -> allDirs.add(curDir.addDir(args[1]))
            else -> curDir.addFile(AcFile(args[1], args[0].toInt()))
        }
    }
    
    val allDirsFiltered = allDirs.filter { it.getSize() <= 100000 }
    val sumDirsSize = allDirsFiltered.sumOf { it.getSize() }
    println("all dirs with size <= 100000: ${allDirsFiltered.map { "${it.name} ${it.getSize()}" }}")
    println("total filtered dirs size: $sumDirsSize")
    
    val needFree = baseDir.getSize() - 40000000    
    val dirToDelete = allDirs.filter { it.getSize() >= needFree }.minByOrNull { it.getSize() }!!
    
    println("Total occupied size: ${baseDir.getSize()}")
    println("Need to free: $needFree")
    println("Need to delete dir: ${dirToDelete.name} with size ${dirToDelete.getSize()}")
}