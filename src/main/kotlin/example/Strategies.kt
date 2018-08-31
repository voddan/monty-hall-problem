package example

import simulation.GameStrategy
import java.util.*

class ConstantStrategy : GameStrategy() {
    override fun invoke(closedIndices: List<Int>): Int {
        assert(0 in closedIndices)
        return 0
    }
}

class RandomStrategy : GameStrategy() {
    val random = Random()

    override fun invoke(closedIndices: List<Int>): Int {
        return closedIndices[random.nextInt(closedIndices.size)]
    }
}

class DriftStrategy : GameStrategy() {
    var size: Int = -1
    var index: Int = -1

    override fun invoke(closedIndices: List<Int>): Int {
        if(size < 0) size = closedIndices.size

        do {
            index = (index + 1) % size
        } while(index !in closedIndices)

        return index
    }
}

class HesitantStrategy : GameStrategy() {
    var index: Int = 1

    override fun invoke(closedIndices: List<Int>): Int {
        index = (1 - index).coerceIn(closedIndices.indices)
        return closedIndices[index]
    }
}