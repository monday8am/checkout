package com.monday8am.checkout.helpers


// Based on https://github.com/danneu/kotlin-result

typealias NetworkResult<V, E> = Result<V, E>

fun <V, E> Result<List<V>, E>.asList() = when (this) {
    is Result.Ok<List<V>> -> value
    is Result.Err<E> -> listOf()
    is Result.Loading -> listOf()
}

fun <V, E> Result<V, E>.getOrElse(default: V) = when (this) {
    is Result.Ok<V> -> value
    is Result.Err<E> -> default
    is Result.Loading -> default
}

fun <V, E> Result<V, E>.getOrNull() = when (this) {
    is Result.Ok<V> -> value
    is Result.Err<E> -> null
    is Result.Loading -> null
}

fun <V, V2, E> Result<V, E>.flatMap(transformValue: (V) -> Result<V2, E>): Result<V2, E> = when (this) {
    is Result.Ok<V> -> transformValue(value)
    is Result.Err<E> -> this
    is Result.Loading ->  this
}

sealed class Result <out V, out E> {

    fun <V2> map(transformValue: (V) -> V2): Result<V2, E> = when (this) {
        is Ok -> Ok(transformValue(value))
        is Err -> this
        is Loading -> this
    }

    object Loading : Result<Nothing, Nothing>()

    data class Ok <out V > internal constructor (val value: V): Result<V, Nothing>() {
        override fun toString() = "Result.Ok($value)"
        override fun hashCode() = value?.hashCode() ?: 0
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Ok<*> && value == other.value
        }
    }

    data class Err <out E> internal constructor (val error: E): Result<Nothing, E>() {
        override fun toString() = "Result.Err($error)"
        override fun hashCode() = error?.hashCode() ?: 0
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Err<*> && error == other.error
        }
    }

    companion object {
        fun <V> ok (value: V): Result.Ok<V> = Ok(value)
        fun <E> err (error: E): Result.Err<E> = Result.Err(error)
    }
}
