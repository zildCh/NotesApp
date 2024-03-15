package com.github.zottaa.authorization.login

import com.github.zottaa.authorization.UserCredits
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface LoginService {
    @PATCH("users/")
    suspend fun login(@Body userCredits: UserCredits): ServerUser
}