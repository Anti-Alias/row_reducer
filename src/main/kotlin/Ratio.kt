
/**
 * Represents an immutable Ratio with a numerator and a denominator
 */
data class Ratio(val numerator:Int, val denominator:Int) {

    /**
     * Checks for zero denominator
     */
    init {
        if(denominator == 0)
            throw ArithmeticException("Cannot create a Ratio with a denominator of zero.")
    }

    /**
     * Alternate constructor that only takes numerator.
     * Denominator is set to 1.
     */
    constructor(numerator:Int) : this(numerator, 1)


    /**
     * Returns a simplified version of this Ratio.
     */
    fun simplified():Ratio {
        val gcd:Int = Math.gcd(numerator, denominator)
        return Ratio(numerator / gcd, denominator / gcd)
    }

    /**
     * This ratio plus another Ratio.
     * Does not simplify.
     */
    operator fun plus(that:Ratio):Ratio {
        val lcmDen = Math.lcm(this.denominator, that.denominator)
        val thisScale = lcmDen / this.denominator
        val thatScale = lcmDen / that.denominator
        val thisNum = this.numerator * thisScale
        val thatNum = that.numerator * thatScale
        return Ratio(thisNum + thatNum, lcmDen)
    }

    /**
     * This ratio plus another Ratio.
     * Does not simplify.
     */
    operator fun minus(that:Ratio):Ratio {
        val lcmDen = Math.lcm(this.denominator, that.denominator)
        val thisScale = lcmDen / this.denominator
        val thatScale = lcmDen / that.denominator
        val thisNum = this.numerator * thisScale
        val thatNum = that.numerator * thatScale
        return Ratio(thisNum - thatNum, lcmDen)
    }

    /**
     * This times another Ratio.
     * Does not simplify.
     */
    operator fun times(that:Ratio):Ratio {
        return Ratio(this.numerator * that.numerator, this.denominator * that.denominator)
    }

    /**
     * This divided by another Ratio.
     * Does not simplify.
     */
    operator fun div(that:Ratio):Ratio {
        return Ratio(this.numerator * that.denominator, this.denominator * that.numerator)
    }
    /**
     * This ratio plus a number.
     * Does not simplify.
     */
    operator fun plus(num:Int):Ratio {
        return Ratio(numerator + num * denominator, denominator)
    }

    /**
     * This minus plus a number.
     * Does not simplify.
     */
    operator fun minus(num:Int):Ratio {
        return Ratio(numerator - num * denominator, denominator)
    }

    /**
     * This ratio multiplied by a scalar.
     * Does not simplify.
     */
    operator fun times(s:Int):Ratio {
        return Ratio(numerator * s, denominator)
    }

    /**
     * This ratio divided by a scalar.
     * Does not simplify.
     */
    operator fun div(s:Int):Ratio {
        return Ratio(numerator, denominator * s)
    }

    /**
     * Compares this Ratio with another.
     */
    override fun equals(that: Any?):Boolean {
        return when(that) {
            is Ratio -> this.numerator * that.denominator == that.numerator * this.denominator
            is Int -> this.numerator == that * this.denominator
            else -> false
        }
    }

    /**
     * Simply this Ratio.
     */
    operator fun unaryPlus() = this

    /**
     * Negates this Ratio object.
     */
    operator fun unaryMinus() = Ratio(-numerator, denominator)

    /**
     * Reciprocal of this Ratio.
     */
    fun reciprocal() = Ratio(denominator, numerator)

    /**
     * Converts this Ratio to a Double
     */
    fun toDouble():Double {
        return numerator / denominator.toDouble()
    }

    /**
     * Converts th is Ratio to a Float
     */
    fun toFloat():Float {
        return numerator / denominator.toFloat()
    }

    /**
     * String representation of Ratio.
     */
    override fun toString():String {
        return  if(denominator == 1) numerator.toString()
                else if(numerator == 0) "0"
                else "$numerator/$denominator"
    }

    companion object {

        /**
         * ZERO ratio
         */
        val ZERO = Ratio(0)
        val ONE = Ratio(1)
        val TWO = Ratio(2)
        val THREE = Ratio(3)
        val HALF = Ratio(1, 2)

        /**
         * Parses an expression as a Ratio
         * @param expr Ratio expression. Should be <int> / <int>
         * Whitespace is ignored.
         */
        fun parse(expr:String):Ratio {
            val split:List<String> = expr.split("/").map{ it.trim() }
            val numerator:Int = split[0].trim().toInt()
            val denominator:Int = if(split.size == 1) 1 else split[1].toInt()
            return Ratio(numerator, denominator)
        }
    }
}