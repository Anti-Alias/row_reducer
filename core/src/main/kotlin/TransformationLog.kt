/**
 * Represents a log of all transnformations made.
 * @property initial Initial matrix state
 *
 * First transformation of matrix is at index 0, and
 * result of that transformation is at index 0.
 */
class TransformationLog(val initial:Matrix) {
    private val steps = mutableListOf<Step>()

    /**
     * Adds a transformation on the last matrix made, and the supposed
     * resulting matrix.
     *
     * @param trans Transformation on given matrix.
     * @param result Supposed resulting Matrix of transformation.
     * @return this TransformationLog
     */
    fun add(trans:Transformation, result:Matrix):TransformationLog {
        steps.add(Step(trans, result))
        return this
    }

    /**
     * Gets a step at the specified index.
     */
    operator fun get(index:Int):Step {
        return steps[index]
    }

    /**
     * Number of transformations/resulting matrices.
     * Equal to the number of times [log] has been invoked on this instance.
     */
    val size get() = steps.size

    /**
     * Stringifies this Log
     */
    override fun toString():String {
        val builder = StringBuilder()

        builder.append("Initial matrix:\n").append(initial).append('\n')
        for(i in 0 until steps.size) {
            val step = steps[i]
            builder
                .append("Step ").append(i+1).append(":\n")
                .append(step.trans).append('\n')
                .append(step.result).append('\n')
        }

        return builder.toString()
    }

    /**
     * Simple helper class for representing a step in the TransformationLog
     */
    data class Step(val trans:Transformation, val result:Matrix)
}