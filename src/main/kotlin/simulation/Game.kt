package simulation

abstract class GameStrategy {
    abstract fun invoke(closedIndices: List<Int>): Int
}


class Game(val lottery: Lottery, val strategy: GameStrategy) {
    val recordStringBuilder = StringBuilder()

    fun play(): Boolean {
        assert(recordStringBuilder.isEmpty())

        do {
            val guessIndex = strategy.invoke(lottery.closedIndices)
            assert(lottery.boxes[guessIndex].isClosed)

            recordStringBuilder.append(lottery.diagram(guessIndex))

            if(!lottery.shouldKeepGoing(guessIndex))
                return lottery.boxes[guessIndex] is PriseBox

            lottery.openBoxesExcept(guessIndex)
        } while (true)
    }
}