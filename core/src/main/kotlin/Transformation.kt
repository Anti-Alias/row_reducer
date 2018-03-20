/***
 * Type representing a transformation on a Matrix.
 */
interface Transformation

/**
 * Represents the swapping of two rows.
 */
data class Swap(val rowA:Int, val rowB:Int) : Transformation {
    override fun toString() = "Swapping row ${rowA+1} with ${rowB+1}"
}

/**
 * Represents adding [destRow] by [srcRow] * [scale]
 */
data class Add(val destRow:Int, val srcRow:Int, val scale:Ratio) : Transformation {
    override fun toString() = "Adding row ${destRow+1} by $scale x row ${srcRow+1}"
}

/**
 * Represents scaling a given row by [scale]
 */
data class Scale(val row:Int, val scale:Ratio) :Transformation {
    override fun toString() = "Scaling row ${row+1} by $scale"
}