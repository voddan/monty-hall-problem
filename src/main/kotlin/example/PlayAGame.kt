package example.playAGame

import simulation.Game
import simulation.Lottery
import simulation.LotteryRules

fun main(args: Array<String>) {

    val rules = LotteryRules(numPriseBoxes = 1, numEmptyBoxes = 9, numOpenBoxes = 1)
    val lottery = Lottery(rules)

    val game = Game(lottery) { closedIndices -> closedIndices.first() }

    val result = game.play()

    print(game.recordStringBuilder)
    print(result)
}

