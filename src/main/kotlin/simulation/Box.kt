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

    abstract fun symbol(): String
}

class PriseBox : Box() {
    override fun symbol() = if(isClosed) """🤗X""" else """!️🎁"""
}

class EmptyBox : Box() {
    override fun symbol() = if(isClosed) """C""" else """🍋_"""
}