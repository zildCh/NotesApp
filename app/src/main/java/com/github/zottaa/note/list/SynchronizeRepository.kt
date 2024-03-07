package com.github.zottaa.note.list

import com.github.zottaa.core.UserNotesResult
import retrofit2.HttpException
import java.net.UnknownHostException

interface SynchronizeRepository {
    suspend fun synchronize(userId: Long): UserNotesResult

    class Base(
        private val service: SynchronizeService
    ) : SynchronizeRepository {
        override suspend fun synchronize(userId: Long): UserNotesResult {
            return try {
                val response = service.synchronize(userId.toString())
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