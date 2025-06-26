package `2022`

import readResource
import java.lang.Integer.max

fun day13() {
    
    class Packet(val parent: Packet? = null) : Comparable<Packet> {
        var text: String? = null

        constructor(text: String) : this() {
            this.text = text
            parse(text)            
        }
        
        val elements = mutableListOf<Any>()

        fun add(value: Any) {
            elements.add(value)
        }

        fun makeSinglePacket(intValue: Int) : Packet {
            return Packet(this).apply { add(intValue) }
        }

        override fun toString(): String {
            return text ?: super.toString()
        }

        override fun compareTo(other: Packet): Int {
            for (i in 0..max(elements.lastIndex, other.elements.lastIndex)) {
                if (i > elements.lastIndex) {
                    return -1
                } else if (i > other.elements.lastIndex) {
                    return 1
                }
                
                val left = elements[i]
                val right = other.elements[i]
                var result = 0

                if (left is Int && right is Int) {
                    result = left - right
                } else if (left is Packet && right is Packet) {
                    result = left.compareTo(right)
                } else if (left is Packet) {
                    if (left.elements.size == 0) {
                        return -1
                    }
                    result = left.compareTo(makeSinglePacket(right as Int))
                } else if (right is Packet) {
                    if (right.elements.size == 0) {
                        return 1
                    }
                    result = makeSinglePacket(left as Int).compareTo(right)
                }

                if (result == 0) {
                    continue
                }
                return result
            }
            return 0
        }

        private fun parse(text: String) {
            var curPacket = this
            for (i in 0..text.lastIndex) {
                val char = text[i]
                if (char.isDigit()) {
                    if (text[i - 1].isDigit()) {
                        val last = curPacket.elements.lastIndex
                        curPacket.elements[last] = (curPacket.elements[last] as Int) * 10 + char.digitToInt()
                    } else {
                        curPacket.add(char.digitToInt())
                    }
                } else if (char == '[') {
                    val newPacket = Packet(curPacket)
                    curPacket.add(newPacket)
                    curPacket = newPacket
                } else if (char == ']') {
                    curPacket = curPacket.parent!!
                }
            }
        }
    }

    val lines = readResource("2022/day13.txt")
    
    val allPacketPairs = mutableListOf<Pair<Packet, Packet>>()
    for (i in 0..lines.lastIndex step 3) {
        val packetLeft = Packet(lines[i])
        val packetRight = Packet(lines[i + 1])
        allPacketPairs.add(Pair(packetLeft, packetRight))
    }

    var sumIndicesRightOrder = 0
    for ((i, pair) in allPacketPairs.withIndex()) {
        if (pair.first < pair.second) {
            sumIndicesRightOrder += (i + 1)
        }
    }
    
    println("Sums of indices of pairs in right order: $sumIndicesRightOrder")
    
    val allPackets = (allPacketPairs.map { it.toList() }.flatten() + Packet("[[2]]") + Packet("[[6]]")).sorted()
    val index2 = allPackets.indexOfFirst { it.text == "[[2]]" } + 1
    val index6 = allPackets.indexOfFirst { it.text == "[[6]]" } + 1
    
    println("Indices of decoder key multiplied: ${index2 * index6}")
} 