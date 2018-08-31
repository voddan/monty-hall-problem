package simulation

/**
 * Immutable data structure to hold parameters of the lottery.
 *
 * @param numPriseBoxes the number of boxes with prises
 * @param numEmptyBoxes the number of empty boxes
 * @param numOpenBoxes  the number of boxes that are opened each turn
 * */
data class LotteryRules(val numPriseBoxes: Int, val numEmptyBoxes: Int, val numOpenBoxes: Int)

/**
 * [Lottery] is a raw of boxes that are randomly generated according to the [rules].
 *
 * In the beginning all boxes are closed.
 * On each turn the player may select one box and ask the host to open some of the other boxes.
 * Player is free to change her selection any time.
 *
 * Note: this is a mutating class!
 * */
class Lottery(val rules: LotteryRules) {
    val boxes: List<Box> = (List(rules.numPriseBoxes) { PriseBox() } + List(rules.numEmptyBoxes) { EmptyBox() }).shuffled()

    val closedIndices: List<Int> get() = boxes.mapIndexedNotNull { i, box -> if(box.isClosed) i else null}
    val closedBoxes: List<Box> get() = boxes.slice(closedIndices)

    /**
     * Opens [LotteryRules.numOpenBoxes] empty boxes except for the selected box on [index].
     *
     * @param index index of the selected box
     * */
    fun openBoxesExcept(index: Int) {
        val chosenBox = boxes[index]
        assert(chosenBox.isClosed)

        val leftBoxes = (closedBoxes - chosenBox).filterIsInstance<EmptyBox>()
        leftBoxes.shuffled().take(rules.numOpenBoxes).forEach { it.open() }
    }

    /**
     * @returns if the next turn of the game should be played.
     *
     * @param index index of the selected box
     * */
    fun shouldKeepGoing(index: Int): Boolean {
        val anyEmptyBoxesLeft = (closedBoxes - boxes[index]).any { !it.hasPrize }

        return closedBoxes.size > 2 && anyEmptyBoxesLeft
    }

    /**
     * @returns a one-line representation of the state of the lottery
     *
     * Diagrams of all possible states should have the same length
     * */
    fun diagram(selectedIndex: Int): String {
        val selectedBox = boxes[selectedIndex]

        return boxes.joinToString(separator = "", prefix = "[", postfix = "]\n") { box ->
            if(box == selectedBox)
                "(" + box.diagram() + ")"
            else
                " " + box.diagram() + " "
        }
    }
}