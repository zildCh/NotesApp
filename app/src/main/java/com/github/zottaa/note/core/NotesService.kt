package com.github.zottaa.note.core

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesService {

    @POST("users/{userId}/category/{categoryId}/notes")
    suspend fun create(
        @Path("userId") userId: Long,
        @Path("categoryId") categoryId: Long,
        @Body noteBody: NoteBody
    ) : Long

    @PUT("users/{userId}/category/{categoryId}/notes/{noteId}/")
    suspend fun update(
        @Path("userId") userId: Long,
        @Path("categoryId") categoryId: Long,
        @Path("noteId") noteId: Long,
        @Body noteBody: NoteBody
    )

    @DELETE("users/{userId}/category/{categoryId}/notes/{noteId}/")
    suspend fun delete(
        @Path("userId") userId: Long,
        @Path("categoryId") categoryId: Long,
        @Path("noteId") noteId: Long
    )
}