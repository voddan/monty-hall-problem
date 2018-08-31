package simulation

typealias Strategy = (closedIndices: List<Int>) -> Int

fun byIndex(strategyByIndex: (count: Int) -> Int): Strategy = { indices -> indices[strategyByIndex(indices.size)]}

class Game(val lottery: Lottery, val strategy: Strategy) {
    val recordStringBuilder = StringBuilder()

    fun playOne(): Result {
        val guessIndex = strategy(lottery.closedIndices)
        assert(lottery.boxes[guessIndex].isClosed)

        recordStringBuilder.append(printLottery(guessIndex))

        val status = lottery.check(guessIndex)
        lottery.openBoxesExcept(guessIndex)

        return status
    }

    fun play(): Boolean {
        assert(recordStringBuilder.isEmpty())

        var status: Result
        do {
            status = playOne()

        } while (status == Result.KEEP_GOING)

        return (status == Result.WIN)
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