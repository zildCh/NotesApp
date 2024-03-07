package com.github.zottaa.authorization.registration

import com.github.zottaa.authorization.UserCredits
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {
    @POST("users/")
    suspend fun registration(@Body userCredits: UserCredits)
}