package ponchonog.funky

import org.apache.commons.lang3.SystemUtils
import org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode
import org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString
import org.apache.commons.lang3.builder.ToStringStyle

sealed class Result<out T> : Value() {
    class Failure(val value: ErrorCode) : Result<Nothing>()
    class Success<out T>(val value: T) : Result<T>()
}

fun <T> tryCatchNonFatal(f: () -> T): Result<T> =
    try {
        Result.Success(f())
    } catch (throwable: Throwable) {
        if (throwable.isNonFatal())
            Result.Failure(object : ErrorCode {})
        else
            throw throwable
    }

private fun Throwable.isNonFatal() =
    when (this) {
    // StackOverflowError ok even though it is a VirtualMachineError
        is StackOverflowError                                                            -> true

    // VirtualMachineError includes OutOfMemoryError and other fatal errors
        is VirtualMachineError, is ThreadDeath, is InterruptedException, is LinkageError -> false

        else                                                                             -> true
    }

interface ErrorCode

open class Value {
    override fun equals(other: Any?): Boolean = reflectionEquals(this, other)

    override fun hashCode(): Int = reflectionHashCode(this)

    override fun toString(): String = reflectionToString(this, object : ToStringStyle() {
        init {
            // Make output look similar to Kotlin data classes.
            contentStart = "("
            contentEnd = SystemUtils.LINE_SEPARATOR + ")"

            // Multi-line output makes diffing test failures easier.
            fieldSeparator = SystemUtils.LINE_SEPARATOR + "  "
            isFieldSeparatorAtStart = true

            // Don't show identity hash code to make it easier to diff test failure diagnostics.
            isUseIdentityHashCode = false

            isUseShortClassName = true
        }
    })
}