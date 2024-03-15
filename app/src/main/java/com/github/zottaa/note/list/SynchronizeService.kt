package com.github.zottaa.note.list

import com.github.zottaa.authorization.login.ServerUser
import retrofit2.http.GET
import retrofit2.http.Path

interface SynchronizeService {
    @GET("users/{userId}/")
    suspend fun synchronize(@Path("userId") userId: String): ServerUser
}