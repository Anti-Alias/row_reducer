/**
 * Represents an immutable n-dimensional Vector of Ratios.
 */
class Vector private constructor (val elements: Array<out Ratio>) {

    /**
     * Number of Ratios in this Vector
     */
    val size:Int get() = elements.size

    /**
     * Gets Ratio at given index
     */
    operator fun get(index:Int):Ratio = elements[index]

    /**
     * Map values in Vector to new values
     */
    fun map(func: (Ratio)->Ratio):Vector {
        val newAry = Array(size){ i -> func(elements[i]) }
        return Vector(newAry)
    }

    /**
     * Static context
     */
    companion object {

        /**
         * Constructs a Vector with the given ratios.
         */
        fun of(vararg ratios: Ratio) = Vector(ratios)
    }
}