package ponchonog.funky

data class NonEmptyList<out T>(val head: T, val tail: List<T>) {

    constructor(head: T, vararg tail: T) : this(head, tail.asList())

    fun asList(): List<T> = listOf(head).plus(tail)

    fun <U> map(f: (T) -> U): NonEmptyList<U> =
        nel(f(head), tail.map(f))

    companion object {

        fun <T> nel(head: T, vararg tail: T) =
            nel(head, tail.asList())

        fun <T> nel(head: T, tail: List<T>) =
            NonEmptyList(head, tail)
    }
}