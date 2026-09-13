package com.example.data.repository

import com.example.data.local.BookmarkDao
import com.example.data.local.BookmarkEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    val bookmarks: Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()

    fun isBookmarked(id: String): Flow<Boolean> = bookmarkDao.isBookmarked(id)

    suspend fun toggleBookmark(id: String, type: String, title: String, category: String, subtitle: String, currentlyBookmarked: Boolean) {
        if (currentlyBookmarked) {
            bookmarkDao.deleteBookmarkById(id)
        } else {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    id = id,
                    type = type,
                    title = title,
                    category = category,
                    subtitle = subtitle
                )
            )
        }
    }

    suspend fun removeBookmark(id: String) {
        bookmarkDao.deleteBookmarkById(id)
    }
}
