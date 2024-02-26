package com.github.zottaa.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: CategoryCache)

    @Query("SELECT * FROM category")
    suspend fun categories(): List<CategoryCache>

    @Query("DELETE FROM category where category_id = :categoryId")
    suspend fun delete(categoryId: Long)

    @Query("SELECT * FROM category where category_id = :categoryId")
    suspend fun category(categoryId: Long): CategoryCache
}