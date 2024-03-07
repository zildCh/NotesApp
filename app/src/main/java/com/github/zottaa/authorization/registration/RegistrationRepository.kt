package com.github.zottaa.authorization.registration

import com.github.zottaa.authorization.UserCredits
import retrofit2.HttpException
import retrofit2.await
import java.lang.IllegalStateException
import java.net.UnknownHostException


interface RegistrationRepository {
    suspend fun registration(nickname: String, password: String): RegistrationResult

    class Base(
        private val service: RegistrationService
    ) : RegistrationRepository {
        override suspend fun registration(nickname: String, password: String): RegistrationResult {
            return try {
                service.registration(UserCredits(nickname, password))
                RegistrationResult.Success
            } catch (e: HttpException) {
                RegistrationResult.Error(e.message ?: "HTTP Error")
            } catch (e: UnknownHostException) {
                RegistrationResult.Error(e.message ?: "Unknown Host")
            } catch (e: IllegalStateException) {
                RegistrationResult.Error(e.message ?: "Illegal State")
            }
        }
    }
}