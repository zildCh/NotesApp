package com.github.zottaa.note.core

import retrofit2.HttpException
import java.net.UnknownHostException

interface RemoteNotesRepository {

    interface Create {
        suspend fun create(
            userId: Long,
            noteTitle: String,
            noteText: String,
            noteUpdateTime: Long,
            categoryId: Long
        ): CreateResult
    }

    interface Edit {
        suspend fun update(
            userId: Long,
            noteId: Long,
            noteTitle: String,
            noteText: String,
            noteUpdateTime: Long,
            categoryId: Long
        ): UpdateResult

        suspend fun delete(userId: Long, noteId: Long, categoryId: Long): DeleteResult
    }

    interface All : Create, Edit

    class Base(
        private val service: NotesService
    ) : All {
        override suspend fun create(
            userId: Long,
            noteTitle: String,
            noteText: String,
            noteUpdateTime: Long,
            categoryId: Long
        ): CreateResult {
            return try {
                val response = service.create(
                    userId,
                    categoryId,
                    NoteBody(noteTitle, noteText, noteUpdateTime)
                )
                CreateResult.Success(response)
            } catch (e: HttpException) {
                CreateResult.Error(e.message ?: "HTTP Error")
            } catch (e: UnknownHostException) {
                CreateResult.Error(e.message ?: "Unknown Host")
            } catch (e: IllegalStateException) {
                CreateResult.Error(e.message ?: "Illegal State")
            }
        }

        override suspend fun update(
            userId: Long,
            noteId: Long,
            noteTitle: String,
            noteText: String,
            noteUpdateTime: Long,
            categoryId: Long
        ): UpdateResult {
            return try {
                service.update(
                    userId,
                    categoryId,
                    noteId,
                    NoteBody(noteTitle, noteText, noteUpdateTime)
                )
                UpdateResult.Success
            } catch (e: HttpException) {
                UpdateResult.Error(e.message ?: "HTTP Error")
            } catch (e: UnknownHostException) {
                UpdateResult.Error(e.message ?: "Unknown Host")
            } catch (e: IllegalStateException) {
                UpdateResult.Error(e.message ?: "Illegal State")
            }
        }

        override suspend fun delete(userId: Long, noteId: Long, categoryId: Long): DeleteResult {
            return try {
                service.delete(
                    userId,
                    categoryId,
                    noteId,
                )
                DeleteResult.Success
            } catch (e: HttpException) {
                DeleteResult.Error(e.message ?: "HTTP Error")
            } catch (e: UnknownHostException) {
                DeleteResult.Error(e.message ?: "Unknown Host")
            } catch (e: IllegalStateException) {
                DeleteResult.Error(e.message ?: "Illegal State")
            }
        }
    }
}