package com.signal.data.util

import com.signal.domain.exception.BadRequestException
import com.signal.domain.exception.ConflictException
import com.signal.domain.exception.ForbiddenException
import com.signal.domain.exception.NotFoundException
import com.signal.domain.exception.OfflineException
import com.signal.domain.exception.OnServerException
import com.signal.domain.exception.TimeoutException
import com.signal.domain.exception.TooLargeRequestException
import com.signal.domain.exception.UnAuthorizationException
import com.signal.domain.exception.UnknownException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandler<T> {

    private lateinit var httpRequest: suspend () -> T

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            val code = e.code()
            throw when (code) {
                400 -> BadRequestException()
                401 -> UnAuthorizationException()
                403 -> ForbiddenException()
                404 -> NotFoundException()
                409 -> ConflictException()
                413 -> TooLargeRequestException()
                in 500..599 -> OnServerException()
                else -> UnknownException()
            }
        } catch (e: KotlinNullPointerException) {
            throw e
        } catch (e: SocketTimeoutException) {
            throw TimeoutException()
        } catch (e: UnknownHostException) {
            throw OfflineException()
        } catch (e: Throwable) {
            throw UnknownException()
        }
}
