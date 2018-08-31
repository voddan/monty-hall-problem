package example.playAGame

import example.ConstantStrategy
import simulation.Game
import simulation.Lottery
import simulation.LotteryRules

val rules = LotteryRules(numPriseBoxes = 1, numEmptyBoxes = 9, numOpenBoxes = 1)

/**
 * Play one game and see its diagram in the console.
 *
 * For adjustment try changing parameters in [rules]
 * */
fun main(args: Array<String>) {
    val lottery = Lottery(rules)

    val game = Game(lottery, ConstantStrategy())

    val result = game.play()

    print(game.diagram)
    print(result)
}

