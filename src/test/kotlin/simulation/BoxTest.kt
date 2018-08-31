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

    @Test fun `Boxes' diagrams are never blank`() {
        val box1 = EmptyBox()
        val s11 = box1.diagram()
        box1.open()
        val s12 = box1.diagram()


        val box2 = PriseBox()
        val s21 = box2.diagram()
        box2.open()
        val s22 = box2.diagram()

        assertTrue(s11.isNotBlank())
        assertTrue(s12.isNotBlank())
        assertTrue(s21.isNotBlank())
        assertTrue(s22.isNotBlank())
    }

    @Test fun `Boxes have distinct diagrams for all states`() {
        val box1 = EmptyBox()
        val s11 = box1.diagram()
        box1.open()
        val s12 = box1.diagram()

        val box2 = PriseBox()
        val s21 = box2.diagram()
        box2.open()
        val s22 = box2.diagram()

        assertTrue(listOf(s11, s12, s21, s22).distinct().size == 4) { "`$s11`, `$s12`, `$s21`, `$s22`" }
    }

    @Test fun `Boxes have diagrams of the same length`() {
        val box1 = EmptyBox()
        val s11 = box1.diagram()
        box1.open()
        val s12 = box1.diagram()

        val box2 = PriseBox()
        val s21 = box2.diagram()
        box2.open()
        val s22 = box2.diagram()

        assertEquals(s11.length, s12.length)
        assertEquals(s12.length, s21.length)
        assertEquals(s21.length, s22.length)
    }
}