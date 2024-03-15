package com.github.zottaa.note.core

import com.github.zottaa.core.CategoryCache
import com.github.zottaa.core.CategoryDao
import com.github.zottaa.core.NotesDao
import com.github.zottaa.core.Now
import com.github.zottaa.note.list.CategoryUi
import kotlinx.coroutines.runBlocking

class CategoryRepository {
    interface ReadList {
        suspend fun categories(): List<Category>
    }

    interface Read {
        suspend fun category(categoryId: Long): Category
    }

    interface All : Read, ReadList

    class Base(
        private val dao: CategoryDao,
    ) : All {

        override suspend fun category(categoryId: Long): Category =
            dao.category(categoryId).let { Category(it.id, it.name) }

        override suspend fun categories(): List<Category> =
            dao.categories().map { Category(it.id, it.name) }

    }
}

data class Category(
    private val id: Long,
    private val name: String
) {
    fun toUi(): CategoryUi =
        CategoryUi.Base(id, name)
}