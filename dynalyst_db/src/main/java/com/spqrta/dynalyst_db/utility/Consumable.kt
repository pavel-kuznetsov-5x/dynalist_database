package com.spqrta.dynalyst_db.utility

//todo to reusables
class Consumable<T>(
    val payload: T,
    private var _consumed: Boolean = false
) {
    val consumed: Boolean
        get() = _consumed

    fun consume(c: (payload: T) -> Unit) {
        if (!consumed) {
            _consumed = true
            c.invoke(payload)
        }
    }

    fun <R> map(mapper: (T) -> R): Consumable<R> {
        return Consumable(mapper.invoke(payload), consumed)
    }
}

fun Exception.toConsumable(): Consumable<Exception> {
    return Consumable(this)
}

fun String.toConsumable(): Consumable<String> {
    return Consumable(this)
}