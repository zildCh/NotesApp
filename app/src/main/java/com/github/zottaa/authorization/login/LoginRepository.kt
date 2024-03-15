package com.github.zottaa.authorization.login

import com.github.zottaa.authorization.UserCredits
import com.github.zottaa.core.UserNotesResult
import retrofit2.HttpException
import retrofit2.await
import java.lang.IllegalStateException
import java.net.UnknownHostException

interface LoginRepository {
    suspend fun login(nickname: String, password: String): UserNotesResult

    class Base(
        private val service: LoginService
    ) : LoginRepository {
        override suspend fun login(nickname: String, password: String): UserNotesResult {
            return try {
                val response = service.login(UserCredits(nickname, password))
                UserNotesResult.Success(response)
            } catch (e: HttpException) {
                UserNotesResult.Error(false)
            } catch (e: UnknownHostException) {
                UserNotesResult.Error(true)
            } catch (e: IllegalStateException) {
                UserNotesResult.Error(false)
            }
        }
    }
}