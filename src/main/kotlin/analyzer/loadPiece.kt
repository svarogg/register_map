package analyzer

import java.io.File

fun loadPiece(path: String): Piece {
    File(path).useLines { lines ->
        val iterator = lines.iterator()
        val header = iterator.next()

        val instrumentNames = header.split(",").drop(1)
        val instruments = instrumentNames.map { instrumentName -> Instrument(instrumentName) }

        val bars = iterator.asSequence().map { line ->
            val values = line.split(",")
            return@map Bar(
                number = values[0].toInt(),
                registerMap = instruments.zip(values.drop(1).map { parseRegister(it) }).toMap()
            )
        }
        return Piece(instruments, bars.toList())
    }
}

fun parseRegister(registerString: String): Register {
    if (registerString == "-") {
        return Register(sortedMapOf())
    }

    if (registerString.contains("<->")) {
        val split = registerString.split("<->")
        val (top, bottom, _) = split.map { it.toInt() }
        val total = top - bottom + 1
        val numbersToProportions =
            (bottom..top).associateWith { 1.0 / total }
                .toSortedMap { a, b -> a.compareTo(b) }
        return Register(numbersToProportions)
    }

    val split = registerString.split("->")
    val numbersToProportions = mutableMapOf<Int, Double>()
    val proportionUnit = 1.0 / split.size
    for (registerString in split) {
        val registerNumber = registerString.toInt()
        if (numbersToProportions.contains(registerNumber)) {
            numbersToProportions[registerNumber] =
                numbersToProportions[registerNumber]!! + proportionUnit
        } else {
            numbersToProportions[registerNumber] = proportionUnit
        }
    }
    return Register(numbersToProportions.toSortedMap { a, b -> a.compareTo(b) })
}