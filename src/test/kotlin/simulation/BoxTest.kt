package simulation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BoxTest {

    @Test fun `EmptyBox starts closed and opens on open`() {
        val box = EmptyBox()

        assertTrue(box.isClosed)

        box.open()

        assertFalse(box.isClosed)
    }

    @Test fun `PriseBox starts closed and opens on open`() {
        val box = PriseBox()

        assertTrue(box.isClosed)

        box.open()

        assertFalse(box.isClosed)
    }

    @Test fun `Boxes has different non-blank symbols for states`() {
        val box1 = EmptyBox()
        val s11 = box1.symbol()
        box1.open()
        val s12 = box1.symbol()

        assertTrue(s11.isNotBlank())
        assertTrue(s12.isNotBlank())
        assertNotEquals(s11, s12)

        val box2 = PriseBox()
        val s21 = box2.symbol()
        box2.open()
        val s22 = box2.symbol()

        assertTrue(s21.isNotBlank())
        assertTrue(s22.isNotBlank())
        assertNotEquals(s21, s22)

        assertTrue(listOf(s11, s12, s21, s22).distinct().size == 4) { "`$s11`, `$s12`, `$s21`, `$s22`" }
    }
}