package simulation

/**
 * [Box] starts CLOSED, and can be opened with the [open] method
 *
 * Note: this is a mutating class!
 *
 * Note: equality must be referential equality!
 * */
sealed class Box(val hasPrize: Boolean) {
    enum class State { CLOSED, OPEN }

    var state = State.CLOSED
        private set

    val isClosed get() = (state == State.CLOSED)

    fun open() {
        assert(isClosed)
        state = State.OPEN
    }

    /**
     * @returns a short representation of the state of the box
     *
     * All states of all sub-types of [Box] must have different diagrams of the same length
     * */
    abstract fun diagram(): String
}

class PriseBox : Box(hasPrize = true) {
    override fun diagram() = if(isClosed) "X" else "!"
}

class EmptyBox : Box(hasPrize = false) {
    override fun diagram() = if(isClosed) "C" else "_"
}