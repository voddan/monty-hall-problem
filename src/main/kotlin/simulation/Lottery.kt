package simulation

enum class Result { WIN, LOSS, KEEP_GOING }

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

    fun check(index: Int): Result {
        val anyEmptyBoxesLeft = (closedBoxes - boxes[index]).any { it is EmptyBox }

        if (closedBoxes.size > 1 + 1 && anyEmptyBoxesLeft) {
            return Result.KEEP_GOING
        }

        return when(boxes[index]) {
            is PriseBox -> Result.WIN
            is EmptyBox -> Result.LOSS
        }
    }
}