package example.collectStatistics

import simulation.Game
import simulation.Lottery
import simulation.LotteryRules
import simulation.byIndex
import java.util.*

val rules = LotteryRules(numPriseBoxes = 1, numEmptyBoxes = 9) { left -> if (left > 1) 1 else 0 }
val SAMPLE_SIZE = 10000

fun newLotteries() = List(SAMPLE_SIZE) { Lottery(rules) }

fun main(args: Array<String>) {

    val simpleGames = newLotteries().map { Game(it, byIndex { 0 }) }

    val randomGames = newLotteries().map { Game(it, byIndex { count -> Random().nextInt(count) }) }

    val changeGames = newLotteries().map { Game(it, byIndex { count -> count % 2 }) }

    val driftGames  = newLotteries().map { Game(it, byIndex { count -> 1 }) }

    println("Simple Games: " + simpleGames.sumBy { it.play().toInt() })
    println("Random Games: " + randomGames.sumBy { it.play().toInt() })
    println("Change Games: " + changeGames.sumBy { it.play().toInt() })
    println("Drift  Games: " + driftGames.sumBy { it.play().toInt() })
}


fun Boolean.toInt() = if(this) 1 else 0
