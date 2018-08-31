package simulation

/**
 * [GameStrategy] encapsulates a strategy to play a [Game]
 *
 * Note: may contain mutable state!
 * */
abstract class GameStrategy {
    /**
     * Invoked on every turn of the game.
     *
     * @param closedBoxesIndices indices of all boxes that have not been opened yet
     * @returns index of the box where the player thinks a prize is
     *
     * The returned index must be one of the provided in [closedBoxesIndices]
     * */
    abstract fun invoke(closedBoxesIndices: List<Int>): Int
}

/**
 * [Game] encapsulates a [Lottery] and a [GameStrategy].
 * A game is played via the [play] method according
 *
 * Note: this is a mutating class!
 * */
class Game(val lottery: Lottery, val strategy: GameStrategy) {
    /**
     * Diagram of the game.
     * Should be accessed after the game has been played.
     * */
    val diagram = StringBuilder()

    /**
     * Simulates playing a [lottery] according to [strategy].
     *
     * Writes diagrams of consecutive lottery states to [diagram].
     *
     * Note: may be run only once!
     * */
    fun play(): Boolean {
        assert(diagram.isEmpty())

        do {
            val guessIndex = strategy.invoke(lottery.closedIndices)
            assert(lottery.boxes[guessIndex].isClosed)

            diagram.append(lottery.diagram(guessIndex))

            if(!lottery.shouldKeepGoing(guessIndex))
                return lottery.boxes[guessIndex].hasPrize

            lottery.openBoxesExcept(guessIndex)
        } while (true)
    }
}