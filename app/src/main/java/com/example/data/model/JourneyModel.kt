package com.example.data.model

enum class JourneyCategory(val titleFa: String) {
    ALL("همه رویدادها"),
    LEARNING("یادگیری"),
    PROJECT("پروژه"),
    FAILURE("شکست و تجربه"),
    ACHIEVEMENT("دستاورد"),
    REFLECTION("تأمل"),
    EXPERIMENT("آزمایش")
}

data class JourneyItem(
    val id: String,
    val dayNumber: Int,
    val date: String,
    val title: String,
    val story: String,
    val lesson: String,
    val nextStep: String,
    val category: JourneyCategory,
    val status: String = "منتشر شده"
)
