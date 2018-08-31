package example

import simulation.GameStrategy
import java.util.*

class ConstantStrategy : GameStrategy() {
    override fun invoke(closedBoxesIndices: List<Int>): Int {
        assert(0 in closedBoxesIndices)
        return 0
    }
}

class RandomStrategy : GameStrategy() {
    val random = Random()

    override fun invoke(closedBoxesIndices: List<Int>): Int {
        return closedBoxesIndices[random.nextInt(closedBoxesIndices.size)]
    }
}

class DriftStrategy : GameStrategy() {
    var size: Int = -1
    var index: Int = -1

    override fun invoke(closedBoxesIndices: List<Int>): Int {
        if(size < 0) size = closedBoxesIndices.size

        do {
            index = (index + 1) % size
        } while(index !in closedBoxesIndices)

        return index
    }
}

class HesitantStrategy : GameStrategy() {
    var index: Int = 1

    override fun invoke(closedBoxesIndices: List<Int>): Int {
        index = (1 - index).coerceIn(closedBoxesIndices.indices)
        return closedBoxesIndices[index]
    }
}