package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val type: String, // "CONTENT", "PROJECT", "JOURNEY"
    val title: String,
    val category: String,
    val subtitle: String,
    val timestamp: Long = System.currentTimeMillis()
)
