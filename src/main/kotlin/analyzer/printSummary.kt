package analyzer

fun printSummary(piece: Piece) {
    val counts = piece.instruments.associateWith { mutableMapOf<Int, Double>() }.toMap().toMutableMap()
    for (bar in piece.bars) {
        for ((instrument, register) in bar.registerMap) {
            val registerMap = counts[instrument]
            for ((number, proportion) in register.numbersToProportions) {
                registerMap!![number] = (registerMap[number] ?: 0.0) + proportion
            }
        }
    }

    println("Summary:")
    for ((instrument, registerMap) in counts) {
        println("${instrument.name}:")
        val sorted = registerMap.toList()
            .sortedBy { (_, value) -> 0 - value }
        for ((number, proportion) in sorted) {
            println("  $number: $proportion")
        }
    }
}