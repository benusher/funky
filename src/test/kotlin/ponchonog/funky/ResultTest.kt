package ponchonog.funky

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isA
import com.natpryce.hamkrest.throws
import org.junit.Assert.fail
import org.junit.Test
import ponchonog.funky.Result.Failure
import ponchonog.funky.Result.Success

class ResultTest {

    @Test
    fun `Try and catch non-fatal exceptions`() {
        val result = tryCatchNonFatal {
            throw NullPointerException()
        }

        when (result) {
            is Success -> fail("Should not be a success result")
            is Failure -> assertThat(result.value, isA<ErrorCode>())
        }
    }

    @Test
    fun `Do not catch fatal exceptions`() {
        assertThat({tryCatchNonFatal { throw OutOfMemoryError() } }, throws<OutOfMemoryError>())
    }
}