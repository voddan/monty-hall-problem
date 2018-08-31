package simulation

data class LotteryRules(val numPriseBoxes: Int, val numEmptyBoxes: Int, val numOpenBoxes: Int)

class Lottery(val rules: LotteryRules) {
    val boxes: List<Box> = (List(rules.numPriseBoxes) { PriseBox() } + List(rules.numEmptyBoxes) { EmptyBox() }).shuffled()

    val closedIndices: List<Int> get() = boxes.mapIndexedNotNull { i, box -> if(box.isClosed) i else null}
    val closedBoxes: List<Box> get() = boxes.slice(closedIndices)

    fun openBoxesExcept(index: Int) {
        val chosenBox = boxes[index]
        assert(chosenBox.isClosed)

        val leftBoxes = (closedBoxes - chosenBox).filterIsInstance<EmptyBox>()
        leftBoxes.shuffled().take(rules.numOpenBoxes).forEach { it.open() }
    }

    fun shouldKeepGoing(index: Int): Boolean {
        val anyEmptyBoxesLeft = (closedBoxes - boxes[index]).any { it is EmptyBox }

        return closedBoxes.size > 2 && anyEmptyBoxesLeft
    }
}