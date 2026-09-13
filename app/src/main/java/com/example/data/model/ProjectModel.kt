package com.example.data.model

enum class ProjectStatus(val titleFa: String) {
    LIVE("در حال اجرا"),
    BUILDING("در حال ساخت"),
    BETA("نسخه آزمایشی"),
    IDEA("ایده اولیه"),
    COMPLETED("تکمیل شده")
}

data class ProjectItem(
    val id: String,
    val title: String,
    val status: ProjectStatus,
    val description: String,
    val problem: String,
    val solution: String,
    val architecture: String,
    val technologies: List<String>,
    val lessons: String,
    val githubUrl: String? = null,
    val demoUrl: String? = null,
    val iconName: String = "code"
)
