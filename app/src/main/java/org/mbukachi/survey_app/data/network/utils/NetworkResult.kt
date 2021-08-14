package com.chepsi.survey.app.data.network.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class NetworkResult<out T : Any> {

    /**
     * This is used to represent successful responses (2xx response codes, non empty response bodies)
     */
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()

    /**
     * Used to represent Server errors (non 2xx status code)
     */
    data class ServerError(
        val code: Int? = null,
        val errorBody: ErrorResponse? = null
    ) : NetworkResult<Nothing>()

    /**
     * Used to represent connectivity errors (a request that didn't result in a response)
     */
    object NetworkError : NetworkResult<Nothing>()

    /**
     * Used to represent errors from the API
     */
    object APIError : NetworkResult<Nothing>()
}