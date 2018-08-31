package simulation

enum class State { CLOSED, OPEN }

sealed class Box {
    var state = State.CLOSED
        private set

    val isClosed get() = (state == State.CLOSED)

    fun open() {
        assert(isClosed)
        state = State.OPEN
    }

    abstract fun diagram(): String
}

class PriseBox : Box() {
    override fun diagram() = if(isClosed) """🤗X""" else """!️🎁"""
}

class EmptyBox : Box() {
    override fun diagram() = if(isClosed) """C""" else """🍋_"""
}