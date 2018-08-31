package simulation

enum class Result { WIN, LOSS, KEEP_GOING }

data class LotteryRules(val numPriseBoxes: Int, val numEmptyBoxes: Int, val howManyToOpen: (left: Int) -> Int)

class Lottery(val rules: LotteryRules) {
    val boxes: List<Box> = (List(rules.numPriseBoxes) { PriseBox() } + List(rules.numEmptyBoxes) { EmptyBox() }).shuffled()

    val closedIndices: List<Int> get() = boxes.mapIndexedNotNull { i, box -> if(box.isClosed) i else null}
    val closedBoxes: List<Box> get() = boxes.slice(closedIndices)

    fun openBoxesExcept(index: Int) {
        boxesWeCanOpen(index).forEach { it.open() }
    }

    fun check(index: Int): Result {
        val num = boxesWeCanOpen(index).size

        if(num == 0) {
            return if(boxes[index] is PriseBox) Result.WIN else Result.LOSS
        }

        return Result.KEEP_GOING
    }

    private fun boxesWeCanOpen(guessIndex: Int): List<Box> {
        val chosenBox = boxes[guessIndex]
        assert(chosenBox.isClosed)

        val leftBoxes = (closedBoxes - chosenBox).filterIsInstance<EmptyBox>()

        val num = rules.howManyToOpen(closedBoxes.size - 1)
        assert(num < leftBoxes.size)

        return leftBoxes.shuffled().take(num)
    }
}