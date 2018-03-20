import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import kotlin.test.*


/**
 * Tests toString implementation.
 */
class ToStringTest : Spek({

    given("Ratios") {
        val a = Ratio(1, 2)
        val b = Ratio(3, 10)
        val c = Ratio(52, 31)
        val d = Ratio(5, 1)
        val e = Ratio(0, 27)
        val f = Ratio(-96, -147)


        on("toString()") {
            it("Should match") {
                assertEquals("1/2", a.toString())
                assertEquals("3/10", b.toString())
                assertEquals("52/31", c.toString())
                assertEquals("5", d.toString())
                assertEquals("0", e.toString())
                assertEquals("32/49", f.simplified().toString())
            }
        }
    }
})


class EquivalenceTest : Spek({
    given("Ratios") {
        val a = Ratio(10, 5)
        val b = Ratio(22, 2)
        val c = Ratio(42, 11)
        val d = Ratio(96, 147)
        val e = Ratio(5)
        val f = Ratio(-96, -147)



        on("simplifying the Ratios") {
            it("should match") {
                assertEquals(Ratio(2,1), a.simplified())
                assertEquals(Ratio(11, 1), b.simplified())
                assertEquals(Ratio(42, 11), c.simplified())
            }
        }

        on("comparing the Ratios") {
            it("should match") {
                assertEquals(a, a.simplified())
                assertEquals(d, d.simplified())
                assertEquals(e, e.simplified())
                assertEquals(d, f)
            }
        }
    }
})

/**
 * Tests operations like addition, multiplicate, etc.
 */
class OperationTest : Spek({
    given("Ratios") {
        val a = Ratio(5, 10)
        val b = Ratio(1, 2)
        val c = Ratio(32, 21)
        val d = Ratio(7, 3)
        val e = 25
        val f = 31


        on("addition") {
            it("should match") {
                assertEquals(Ratio(1), a + b)
                assertEquals(Ratio(557, 21), c + e)
           }
        }

        on("subtraction") {
            it("should match") {
                assertEquals(Ratio(0), a - b)
            }
        }

        on("multiplication") {
            it("should match") {
                assertEquals(Ratio(32, 9), c * d)
            }
        }

        on("division") {
            it("should match") {
                assertEquals(Ratio(32*3, 21*7), c / d)
            }
        }
    }
})


/**
 * Tests parsing operations.
 */
class ParseTest : Spek({
    given("Ratio expression") {
        val a = "21 / 3"
        val b = "-32/41"
        val c = "104 /    -87"

        on("parse") {
            assertEquals(Ratio(21,3), Ratio.parse(a))
            assertEquals(Ratio(-32,41), Ratio.parse(b))
            assertEquals(Ratio(-104,87), Ratio.parse(c))
        }
    }
})