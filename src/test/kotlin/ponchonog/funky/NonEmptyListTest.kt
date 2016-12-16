package ponchonog.funky

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import ponchonog.funky.NonEmptyList.Companion.nel

class NonEmptyListTest {

    @Test
    fun `To List`() {
        assertThat(nel("a", "b").asList(), equalTo(listOf("a", "b")))
    }

    @Test
    fun `Map a function over the list`() {
        assertThat(nel(1, 2).map{it + 1}, equalTo(nel(2, 3)))
    }

    @Test
    fun `FlatMap a function over the list`() {
        assertThat(nel(1, 2).flatMap{nel(it+1)}, equalTo(nel(2, 3)))
    }
}