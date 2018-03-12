import kotlin.math.abs

/**
 * Utility object for Math oprations.
 */
object Math {

    /**
     * Calculates greatest common denominator of two integers.
     */
    fun gcd(a:Int, b:Int):Int {
        var a2 = a
        var b2 = b
        while(b2 != 0) {
            var t = b2
            b2 = mod(a2, b2)
            a2 = t
        }
        return a2
    }

    /**
     * Calculates least common multiple of two integers.
     */
    fun lcm(a:Int, b:Int):Int {
        return abs(a * b) / gcd(a, b)
    }

    /**
     * Simple implementation of the modulo operator.
     */
    fun mod(a:Int, b:Int):Int = ((a % b) + b) % b
}