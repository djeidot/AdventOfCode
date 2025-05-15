import java.math.BigDecimal
import java.math.BigInteger

data class DiskMapItem(val id: Int, val blocks: Int, var blocksLeft: Int, var spaces: Int)

fun y24day9() {
    val lines = readResource("y24day9.txt")
    assert(lines.count() == 1)

    val diskMap = mutableListOf<DiskMapItem>()

    for ((id, i) in (lines[0].indices step 2).withIndex()) {
        diskMap.add(DiskMapItem(id, lines[0][i].digitToInt(), lines[0][i].digitToInt(), if (i+1 == lines[0].length) 0 else lines[0][i+1].digitToInt()))
    }

// Part 1
//    val newDiskMap = mutableListOf<DiskMapItem>()
//    var iterf = 0
//    var iterb = diskMap.lastIndex
//
//    while (iterf < iterb) {
//        val item = diskMap[iterf]
//        newDiskMap.add(DiskMapItem(item.id, item.blocksLeft, 0, 0))
//        item.blocksLeft = 0
//        var spaces = item.spaces
//        while (spaces > 0) {
//            val item2 = diskMap[iterb]
//            if (item2.blocksLeft > spaces) {
//                newDiskMap.add(DiskMapItem(item2.id, spaces, 0, 0))
//                item2.blocksLeft -= spaces
//                spaces = 0
//            } else {
//                newDiskMap.add(DiskMapItem(item2.id, item2.blocksLeft, 0, 0))
//                spaces -= item2.blocksLeft
//                iterb--
//            }
//        }
//        iterf++
//    }
//    if (diskMap[iterf].blocksLeft > 0) {
//        newDiskMap.add(DiskMapItem(diskMap[iterf].id, diskMap[iterf].blocksLeft, 0, 0))
//    }

    // Part 2
    val newDiskMap = mutableListOf<DiskMapItem>()
    for (item in diskMap) {
        newDiskMap.add(DiskMapItem(item.id, item.blocks, 0, 0))
        newDiskMap.add(DiskMapItem(0, 0, 0, item.spaces))
    }
    var pos = newDiskMap.lastIndex
    for (id in diskMap.last().id downTo 1) {
        while (newDiskMap[pos].id != id)
            pos--

        val item = newDiskMap[pos]
        for (i in 0 until pos) {
            val spacesDiff = newDiskMap[i].spaces - item.blocks
            if (spacesDiff < 0) {
                continue
            }
            newDiskMap.add(pos + 1, DiskMapItem(0, 0, 0, item.blocks))
            newDiskMap.add(i, newDiskMap.removeAt(pos))
            if (spacesDiff == 0) {
                newDiskMap.removeAt(i + 1)
            } else {
                newDiskMap[i + 1].spaces = spacesDiff
            }
            break
        }
    }


    var checksum = 0L
    var iterf = 0
    for (item in newDiskMap) {
        for (block in 0 until (item.blocks + item.spaces)) {
            checksum += (item.id * iterf)
            iterf++
        }
    }

    println("checksum is $checksum")
}