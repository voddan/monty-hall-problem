package example.collectStatistics

import example.ConstantStrategy
import example.DriftStrategy
import example.HesitantStrategy
import example.RandomStrategy
import example.playAGame.rules
import simulation.*

val rules = LotteryRules(numPriseBoxes = 1, numEmptyBoxes = 9, numOpenBoxes = 1)
val SAMPLE_SIZE = 100000

/**
 * Play [SAMPLE_SIZE] games and see the number of wins for each respective strategy.
 *
 * For adjustment try changing parameters in [rules]
 * */
fun main(args: Array<String>) {

    val simpleGames = newLotteries().map { Game(it, ConstantStrategy()) }

    val randomGames = newLotteries().map { Game(it, RandomStrategy()) }

    val changeGames = newLotteries().map { Game(it, HesitantStrategy()) }

    val driftGames  = newLotteries().map { Game(it, DriftStrategy()) }

    println("Simple Games: " + simpleGames.sumBy { it.play().toInt() })
    println("Random Games: " + randomGames.sumBy { it.play().toInt() })
    println("Change Games: " + changeGames.sumBy { it.play().toInt() })
    println("Drift  Games: " + driftGames.sumBy { it.play().toInt() })
}


fun newLotteries() = List(SAMPLE_SIZE) { Lottery(rules) }

fun Boolean.toInt() = if(this) 1 else 0
