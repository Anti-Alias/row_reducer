import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import kotlin.test.assertEquals




class MatrixParseStringTest : Spek({

    given("A matrix parsed from a string") {

        val a = Matrix.parse(4, 4, """
            1   2   3   4
            5   6/3 -8  12
            5   -3  2   0
            4/7 8   -3  2/3
        """)

        it("should equal a manually created one") {

            val b = Matrix.of(4, 4,
                Ratio(1), Ratio(2), Ratio(3), Ratio(4),
                Ratio(5), Ratio(6, 3), Ratio(-8), Ratio(12),
                Ratio(5), Ratio(-3), Ratio(2), Ratio.ZERO,
                Ratio(4, 7), Ratio(8), Ratio(-3), Ratio(2, 3)
            )
            assertEquals(a, b)
        }
    }
})


class MatrixIdentityTest4 : Spek({
    given("A matrix") {
        val initial = Matrix.parse(4, 4, """
            1 0 0 0
            0 1 0 0
            0 0 1 0
            0 0 0 1
        """)

        it("should should equal the 4x4 identity matrix") {
            assertEquals(Matrix.identity(4), initial)
        }
    }
})

class MatrixIdentityTest3 : Spek({
    given("A matrix") {
        val initial = Matrix.parse(3, 3, """
            1 0 0
            0 1 0
            0 0 1
        """)

        it("should should equal the 3x3 identity matrix") {
            assertEquals(Matrix.identity(3), initial)
        }
    }
})


class MatrixSwapTest : Spek({
    given("A matrix") {
        val expected = Matrix.parse(4, 4, """
            1   2   3   4
            5   6/3 -8  12
            5   -3  2   0
            4/7 8   -3  2/3
        """)

        it("should should be equal to a swapped matrix") {
            val actual = Matrix.parse(4, 4, """
                5   -3  2   0
                4/7 8   -3  2/3
                1   2   3   4
                5   6/3 -8  12
            """).swap(0, 2).swap(1, 3)
            assertEquals(expected, actual)
        }
    }
})

class MatrixAddTest : Spek({
    given("A matrix") {
        val expected = Matrix.parse(4, 4, """
            1  1  1  1
            3  3  3  3
            2  2  2  2
            -1 -1 -1 -1
        """)

        it("should should equal a matrix that has added one row times a scalar to another row.") {
            val actual = Matrix.parse(4, 4, """
                1  1  1  1
                2  2  2  2
                3  3  3  3
                1  1  1  1
            """)
            .addRow(3, 1, Ratio(-1))
            .addRow(1, 0, Ratio.ONE)
            .addRow(2, 1, Ratio(-1, 3))
            assertEquals(expected, actual)
        }
    }
})

class MatrixScaleTest : Spek({
    given("a simple matrix") {
        val expected = Matrix.parse(3, 3, """
            1 1 1
            1 1 1
            1 1 1
        """)

        it("should equal a scaled matrix") {
            val actual = Matrix.parse(3, 3, """
                1/2  1/2  1/2
                1/3  1/3  1/3
                -3/4 -3/4 -3/4
            """)
            .scaleRow(0, Ratio(2))
            .scaleRow(1, Ratio(3))
            .scaleRow(2, Ratio(-4, 3))
            assertEquals(expected, actual)
        }
    }
})

class MatrixSortTest : Spek({
    given("a simple matrix") {
        val expected = Matrix.parse(4, 4, """
            3  0  0  8
            0  -7 0  9
            0  0  1  3
            0  0  0  3
        """)

        it("should equal a sorted matrix") {
            val starting = Matrix.parse(3, 4, """
                1  -2 3  7
                2  1  1  4
                -3 2  2  -10
            """)
            val log = TransformationLog(starting)

            val actual:Matrix = starting.sorted(log)

            println(log)
            assertEquals(expected, actual)
        }
    }
})