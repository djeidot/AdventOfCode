import java.math.BigDecimal
import java.math.BigInteger

data class DiskMapItem(val id: Int, var blocks: Int, val spaces: Int)

fun y24day9() {
    val lines = readResource("y24day9.txt")
    assert(lines.count() == 1)

    val diskMap = mutableListOf<DiskMapItem>()

    for ((id, i) in (lines[0].indices step 2).withIndex()) {
        diskMap.add(DiskMapItem(id, lines[0][i].digitToInt(), if (i+1 == lines[0].length) 0 else lines[0][i+1].digitToInt()))
    }

    val newDiskMap = mutableListOf<DiskMapItem>()
    var iterf = 0
    var iterb = diskMap.lastIndex

    while (iterf < iterb) {
        val item = diskMap[iterf]
        newDiskMap.add(DiskMapItem(item.id, item.blocks, item.spaces))
        var spaces = item.spaces
        while (spaces > 0) {
            val item2 = diskMap[iterb]
            if (item2.blocks > spaces) {
                newDiskMap.add(DiskMapItem(item2.id, spaces, item2.blocks))
                item2.blocks -= spaces
                spaces = 0
            } else {
                newDiskMap.add(DiskMapItem(item2.id, item2.blocks, item2.blocks))
                spaces -= item2.blocks
                iterb--
            }
        }
        iterf++
    }

    var checksum = 0L
    iterf = 0
    for (item in newDiskMap) {
        for (block in 0 until item.blocks) {
            checksum += (item.id * iterf)
            iterf++
        }
    }

    println("checksum is $checksum")
}