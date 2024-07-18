package analyzer

import java.util.*

data class Instrument(val name: String)

data class Register(val numbersToProportions: SortedMap<Int, Double>)

data class Bar(
    val number: Int,
    val registerMap: Map<Instrument, Register>
)

data class Piece(
    val instruments: List<Instrument>,
    val bars: List<Bar>
) {

    fun getBarNumber(i: Int): Bar {
        return bars[i - 1]
    }
}