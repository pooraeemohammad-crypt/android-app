package com.example.data.model

enum class ContentType(val titleFa: String) {
    ARTICLE("مقاله تخصصی"),
    TUTORIAL("آموزش مرحله‌ای"),
    REEL("ویدیو ریل"),
    PROJECT("پروژه عملی"),
    MINI_COURSE("دوره فشرده"),
    NOTE("یادداشت تجربی")
}

enum class ContentLevel(val titleFa: String) {
    ALL("همه سطوح"),
    BEGINNER("مقدماتی"),
    INTERMEDIATE("متوسط"),
    ADVANCED("پیشرفته")
}

data class ContentItem(
    val id: String,
    val title: String,
    val category: String,
    val type: ContentType,
    val level: ContentLevel,
    val durationText: String, // e.g. "۷ دقیقه مطالعه" or "۲ دقیقه مشاهده"
    val excerpt: String,
    val fullContent: String,
    val codeSnippet: String? = null,
    val codeLanguage: String? = null,
    val publishDate: String,
    val relatedIds: List<String> = emptyList(),
    val instagramReelUrl: String? = null,
    val isEngineeringSpecial: Boolean = false
)
