package analyzer

import java.io.File

fun loadPiece(path: String): Piece {
    val bars = mutableListOf<Bar>()

    File(path).useLines { lines ->
        val header = lines.first()

        val instrumentNames = header.split(",").drop(1)
        val instruments = instrumentNames.map { instrumentName -> Instrument(instrumentName) }

        lines.map { line ->
            val values = line.split(",")
            return@map Bar(
                number = values[0].toInt(),
                registerMap = instruments.zip(values.drop(1).map { parseRegister(it) }).toMap()
            )
        }
        return Piece(instruments, bars)
    }
}

fun parseRegister(registerString: String): Register {
    if (registerString.contains("<->")) {
        val split = registerString.split("<->")
        val (top, bottom, _) = split.map { it.toInt() }
        val total = top - bottom + 1
        val numbersToProportions =
            (bottom..top).associateWith { 1.0 / total }
                .toSortedMap { a, b -> a.compareTo(b) }
        return Register(numbersToProportions)
    } else {
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
}