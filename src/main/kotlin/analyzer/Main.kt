package analyzer

import java.io.File

fun main(args: Array<String>) {
    val filename = getFilename(args)
    if (filename == "") {
        return
    }

    val piece = loadPiece(filename)

    println("Instruments: ${piece.instruments.joinToString(", ") { it.name }}")
    println("Bars:")
    piece.bars.forEach { bar ->
        println("Bar ${bar.number}:")
        bar.registerMap.forEach { (instrument, register) ->
            println("  ${instrument.name}: ${register.numbersToProportions}")
        }
    }
}

private fun getFilename(args: Array<String>): String {
    if (args.isEmpty()) {
        println("Please provide a filename as the first argument.")
        return ""
    }

    val filename = args[0]
    val file = File(filename)

    if (!file.exists() || file.isDirectory) {
        println("The provided filename is not valid or it's a directory.")
        return ""
    }
    return filename
}