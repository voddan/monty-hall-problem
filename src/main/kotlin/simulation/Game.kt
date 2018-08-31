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

            recordStringBuilder.append(printLottery(guessIndex))

            if(!lottery.shouldKeepGoing(guessIndex))
                return lottery.boxes[guessIndex] is PriseBox

            lottery.openBoxesExcept(guessIndex)
        } while (true)
    }

    fun printLottery(guessIndex: Int): StringBuilder {
        val sb = StringBuilder()
        sb.append("[")
        for(i in lottery.boxes.indices) {
            val box = lottery.boxes[i]

            if(i == guessIndex)
                sb.append("(${box.symbol()})")
            else
                sb.append(" ${box.symbol()} ")
        }
        sb.append("]\n")

        return sb
    }
}