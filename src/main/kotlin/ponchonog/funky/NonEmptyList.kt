package ponchonog.funky

data class NonEmptyList<out T>(val head: T, val tail: List<T>) {

    constructor(head: T, vararg tail: T) : this(head, tail.asList())

    fun asList(): List<T> = listOf(head).plus(tail)

    fun <U> map(f: (T) -> U): NonEmptyList<U> =
        nel(f(head), tail.map(f))

    fun <B> flatMap(f: (T) -> NonEmptyList<B>): NonEmptyList<B> {
        val b = mutableListOf<B>()
        val p = f(head)
        b += p.head
        b += p.tail
        tail.forEach { a ->
            val p2 = f(a)
            b += p2.head
            b += p2.tail
        }
        val bb = b.toList()
        return nel(bb.head, bb.tail)
    }

    companion object {

        fun <T> nel(head: T, vararg tail: T) =
            nel(head, tail.asList())

        fun <T> nel(head: T, tail: List<T>) =
            NonEmptyList(head, tail)
    }
}

val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()
