package simulation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LotteryTest {

    val simpleRules = LotteryRules(numPriseBoxes = 1, numEmptyBoxes = 9, numOpenBoxes = 1)

    @Test fun `number of boxes is exact`() {
        val lt = Lottery(simpleRules)
        assertEquals(lt.rules.numPriseBoxes + lt.rules.numEmptyBoxes, lt.boxes.size)
    }

    @Test fun `all boxes in the beginning are closed`() {
        val lt = Lottery(simpleRules)
        assertTrue(lt.boxes.all { it.isClosed })
    }

    @Test fun `number of prise boxes in the beginning is exact`() {
        val lt = Lottery(simpleRules)
        assertEquals(lt.rules.numPriseBoxes, lt.boxes.count { it is PriseBox })
    }

    @Test fun `number of empty boxes in the beginning is exact`() {
        val lt = Lottery(simpleRules)
        assertEquals(lt.rules.numEmptyBoxes, lt.boxes.count { it is EmptyBox })
    }

    @Test fun `boxes are distinguishable`() {
        val lt = Lottery(simpleRules)
        assertEquals(lt.boxes.size, lt.boxes.distinct().size) { lt.boxes.toString() }
    }

    @Test fun `all closed indices are always closed`() {
        val lt = Lottery(simpleRules)
        assertTrue(lt.boxes.slice(lt.closedIndices).all { it.isClosed })
        lt.boxes[0].open()
        assertTrue(lt.boxes.slice(lt.closedIndices).all { it.isClosed })
        lt.boxes[5].open()
        lt.boxes[7].open()
        lt.boxes[8].open()
        assertTrue(lt.boxes.slice(lt.closedIndices).all { it.isClosed })
    }

    @Test fun `all non-closed indices are always open`() {
        val lt = Lottery(simpleRules)
        val allIndices = lt.boxes.indices
        assertTrue(lt.boxes.slice(allIndices - lt.closedIndices).all { !it.isClosed })
        lt.boxes[0].open()
        assertTrue(lt.boxes.slice(allIndices - lt.closedIndices).all { !it.isClosed })
        lt.boxes[5].open()
        lt.boxes[7].open()
        lt.boxes[8].open()
        assertTrue(lt.boxes.slice(allIndices - lt.closedIndices).all { !it.isClosed })
    }

    @Test fun `closed boxes always mach closed indices`() {
        val lt = Lottery(simpleRules)
        assertEquals(lt.boxes.slice(lt.closedIndices), lt.closedBoxes)
        lt.boxes[0].open()
        assertEquals(lt.boxes.slice(lt.closedIndices), lt.closedBoxes)
        lt.boxes[5].open()
        lt.boxes[7].open()
        lt.boxes[8].open()
        assertEquals(lt.boxes.slice(lt.closedIndices), lt.closedBoxes)
    }

    private fun Lottery.selectABoxIndex(closedIndex: Int): Int {
        return closedIndices[closedIndex]
    }

    private fun Lottery.openedBoxesIndices(selectedIndex: Int): Pair<List<Int>, List<Int>> {
        val before = closedIndices
        openBoxesExcept(selectedIndex)
        val after = closedIndices

        return Pair(before, after)
    }

    @Test fun `opening boxes opens only empty boxes`() {
        val lt = Lottery(simpleRules)

        val select1 = lt.selectABoxIndex(0)
        val opened1 = lt.openedBoxesIndices(select1).let { (before, after) -> before - after }
        assertTrue(lt.boxes.slice(opened1).all { it is EmptyBox })

        val select2 = lt.selectABoxIndex(4)
        val opened2 = lt.openedBoxesIndices(select2).let { (before, after) -> before - after }
        assertTrue(lt.boxes.slice(opened2).all { it is EmptyBox })

        val select3 = lt.selectABoxIndex(6)
        val opened3 = lt.openedBoxesIndices(select3).let { (before, after) -> before - after }
        assertTrue(lt.boxes.slice(opened3).all { it is EmptyBox })

        val select4 = lt.selectABoxIndex(6)
        val opened4 = lt.openedBoxesIndices(select4).let { (before, after) -> before - after }
        assertTrue(lt.boxes.slice(opened4).all { it is EmptyBox })

        val select5 = lt.selectABoxIndex(3)
        val opened5 = lt.openedBoxesIndices(select5).let { (before, after) -> before - after }
        assertTrue(lt.boxes.slice(opened5).all { it is EmptyBox })


    }

    @Test fun `opening boxes opens only closed boxes`() {
        val lt = Lottery(simpleRules)

        val select1 = lt.selectABoxIndex(0)
        val (before1, after1) = lt.openedBoxesIndices(select1)
        assertTrue(before1.containsAll(after1))

        val select2 = lt.selectABoxIndex(5)
        val (before2, after2) = lt.openedBoxesIndices(select2)
        assertTrue(before2.containsAll(after2))

        val select3 = lt.selectABoxIndex(4)
        val (before3, after3) = lt.openedBoxesIndices(select3)
        assertTrue(before3.containsAll(after3))

        val select4 = lt.selectABoxIndex(4)
        val (before4, after4) = lt.openedBoxesIndices(select4)
        assertTrue(before4.containsAll(after4))

        val select5 = lt.selectABoxIndex(2)
        val (before5, after5) = lt.openedBoxesIndices(select5)
        assertTrue(before5.containsAll(after5))
    }

    @Test fun `opening boxes does not open the selected box`() {
        val lt = Lottery(simpleRules)

        val select1 = lt.selectABoxIndex(0)
        lt.openedBoxesIndices(select1)
        assertTrue(lt.boxes[select1].isClosed)

        val select2 = lt.selectABoxIndex(0)
        lt.openedBoxesIndices(select2)
        assertTrue(lt.boxes[select2].isClosed)

        val select3 = lt.selectABoxIndex(0)
        lt.openedBoxesIndices(select3)
        assertTrue(lt.boxes[select3].isClosed)

        val select4 = lt.selectABoxIndex(0)
        lt.openedBoxesIndices(select4)
        assertTrue(lt.boxes[select4].isClosed)

        val select5 = lt.selectABoxIndex(0)
        lt.openedBoxesIndices(select5)
        assertTrue(lt.boxes[select5].isClosed)

    }
}