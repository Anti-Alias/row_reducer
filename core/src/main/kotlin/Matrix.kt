import java.io.PrintStream

/**
 * Immutable row x column Matrix class.
 * @property rows Number of rows in Matrix.
 * @property columns Number of columns in Matrix.
 */
class Matrix private constructor (
    val rows:Int,
    val columns:Int,
    private val ratios: Array<Ratio>
){

    /**
     * Gets a particular value in the Matrix
     */
    fun get(row:Int, column:Int): Ratio {
        return ratios[row * columns + column]
    }

    /**
     * Swaps two elements
     */
    fun swap(rowA:Int, rowB:Int):Matrix {
        val m = this.copy()
         m.mutSwap(rowA, rowB)
        return m
    }


    /**
     * Multiplies this Matrix by a scalar
     */
    operator fun times(scalar:Ratio):Matrix {
        val m = this.copy()
        m.mutTimes(scalar)
        return m
    }


    /**
     * Divides this Matrix by a scalar
     */
    operator fun div(scalar:Ratio) = times(scalar.reciprocal())

    /**
     * Scales a given row by ratio
     * @param row Row to scale
     * @param scalar Ratio to scale by.
     */
    fun scaleRow(row:Int, scalar:Ratio):Matrix {
        val m = this.copy()
        m.mutScaleRow(row, scalar)
        return m
    }

    /**
     * Adds [srcRow]*[scalar] to [destRow]
     */
    fun addRow(destRow:Int, srcRow:Int, scalar:Ratio):Matrix {
        val m = this.copy()
        m.mutAddRow(destRow, srcRow, scalar)
        return m
    }


    /**
     * @return Matrix sorted by column of leading coefficent.
     * @param log Optional log of transformations being made.
     */
    fun sorted(log: TransformationLog? = null):Matrix {
        val m = this.copy()
        m.mutSortRows(log)
        return m
    }


    /**
     * Gets the column of the leading coefficient.
     * -1 if all zeroes.
     */
    fun leadingCoefficientColumn(row:Int):Int {
        for(column in 0 until columns) {
            if(get(row, column) != Ratio.ZERO)
                return column
        }
        return -1
    }

    /**
     * Creates a Copy of this Matrix.
     */
    fun copy(rows:Int = this.rows, columns:Int = this.columns, ratios: Array<Ratio> = this.ratios.copyOf()):Matrix {
        return Matrix(rows, columns, ratios)
    }

    /**
     * String representation of this Matrix
     */
    override fun toString():String {

        // Gets string versions of ratio
        val strRatios: List<String> = ratios.map { it.toString() }

        // Calculates longest string representation
        var longest = 0
        for (ratio in strRatios) {
            val len: Int = ratio.toString().length
            if (len > longest)
                longest = len
        }

        // Begins building ratios
        val builder = StringBuilder()
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val ratio = get(row, column)
                builder.append(ratio.toString().padStart(longest + 1))
            }
            builder.append('\n')
        }

        // Returns built string
        return builder.toString()
    }

    /**
     * Compares this Matrix with an other
     */
    override fun equals(that:Any?):Boolean {
        return if(that is Matrix) {
            this.rows == that.rows &&
            this.columns == that.columns &&
            this.ratios contentDeepEquals that.ratios
        }
        else false
    }


    /**
     * HashCode implementation courtesy of https://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method
     */
    override fun hashCode():Int {
        var result = 17
        result = 31 * result + rows
        result = 31 * result + columns
        result = 31 * result + ratios.contentDeepHashCode()
        return result
    }


    // --------------- PRIVATE MUTABLE METHODS --------------
    /**
     * Setter for Matrix
     */
    private fun set(row:Int, column:Int, ratio:Ratio) {
        ratios[row * columns + column] = ratio
    }

    /**
     * Mutable swap implementation
     */
    private fun mutSwap(rowA:Int, rowB:Int) {
        for(column in 0 until columns) {
            val indexA:Int = rowA * columns + column
            val indexB:Int = rowB * columns + column
            val temp = ratios[indexA]
            ratios[indexA] = ratios[indexB]
            ratios[indexB] = temp
        }
    }

    private fun mutTimes(scalar:Ratio) {
        for(i in 0 until ratios.size)
            ratios[i] = ratios[i] * scalar
    }

    private fun mutAddRow(destRow:Int, srcRow:Int, scalar:Ratio){
        for(column in 0 until columns) {
            val destIndex:Int = destRow * columns + column
            val currentRatio = ratios[destIndex]
            val srcRatio = ratios[srcRow * columns + column]
            ratios[destIndex] = currentRatio + srcRatio * scalar
        }
    }

    /**
     * Scales a row by a given ratio
     */
    private fun mutScaleRow(row:Int, scalar:Ratio) {
        for(column in 0 until columns) {
            val index:Int = row * columns + column
            val currentRatio = ratios[index]
            ratios[index] = currentRatio * scalar
        }
    }

    private fun mutSimplifyRow(row:Int) {
        for(column in 0 until columns) {
            val index = row * columns + column
            val ratio = ratios[index]
            ratios[index] = ratio.simplified()
        }
    }

    private fun mutSortRows(log: TransformationLog?) {

        var madeSwap:Boolean
        do {

            // Initializes swap var
            madeSwap = false

            // Bubble sorts rows based on earliest leading coefficient
            for (row in 0 until rows - 1) {
                val leadingA: Int = leadingCoefficientColumn(row)
                val leadingB: Int = leadingCoefficientColumn(row + 1)
                if (leadingB < leadingA) {
                    mutSwap(row, row + 1)
                    madeSwap = true
                    log?.add(Swap(row, row + 1), this.copy())
                }
            }
        }
        while(madeSwap)
    }

    private fun mutReduceRowEchelon(log: TransformationLog?) {

        mutSortRows(log)

    }


    // Checks if this Matrix can be reduced to echelon form.
    // Assuems that it is sorted
    private fun checkReduceable() {
        for(row in 0 until rows) {
            for(col in 0 until columns) {
                val ratio = get(row, col)
                if(ratio == Ratio.ZERO)
                    throw RuntimeException("Could not ")
            }
        }
    }


    /**
     * Static context
     */
    companion object {

        /**
         * Constructs a Matrix of Ratios
         */
        fun of(rows:Int, columns:Int, vararg ratios: Ratio):Matrix {
            require(rows >= 0 && columns >= 0) { "Invalid matrix size $rows x $columns" }
            require(ratios.size == rows * columns) { "Array size of ${ratios.size} did not match rows * columns, (${rows * columns})" }
            return Matrix(rows, columns, ratios.toList().toTypedArray())
        }

        /**
         * Constructs a Matrix of Ratios.
         */
        fun of(rows:Int, columns:Int, ratios:Collection<Ratio>):Matrix {
            return Matrix.of(rows, columns, *ratios.toTypedArray())
        }

        /**
         * Parses a row x column matrix.
         * @param rows Number of rows in matrix.
         * @param columns Number of columns in mmatrix.
         * @param matrixString String representing the matrix.
         * Fractional elements are split on whitespace, and the fractions themselves
         * are expected to be in the form:
         * <int> and <int>/<int>
         * @return Matrix parsed from the given string.
         */
        fun parse(rows:Int, columns:Int, matrixString:String):Matrix {
            val ratios:List<Ratio> = matrixString
                .split("\\s+".toRegex())
                .filter{ it.isNotEmpty() }
                .map{ Ratio.parse(it) }
            return Matrix.of(rows, columns, ratios)
        }

        /**
         * Calculates identity matrix.
         */
        fun identity(dimensions:Int):Matrix {
            require(dimensions >= 0) { "Invalid dimensions '$dimensions'" }
            val ratios = Array(dimensions*dimensions){ i ->
                if(i% (dimensions+1) == 0) Ratio.ONE
                else Ratio.ZERO
            }
            return Matrix(dimensions, dimensions, ratios)
        }
    }
}