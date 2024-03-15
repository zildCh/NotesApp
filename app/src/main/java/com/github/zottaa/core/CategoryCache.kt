package com.github.zottaa.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "category"
)
data class CategoryCache(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: Long,
    @ColumnInfo(name = "category_name")
    val name: String
)