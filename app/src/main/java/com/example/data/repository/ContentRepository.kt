package com.example.data.repository

import com.example.data.model.ContentItem
import com.example.data.model.ContentLevel
import com.example.data.model.ContentType
import com.example.data.model.JourneyCategory
import com.example.data.model.JourneyItem
import com.example.data.model.MockDataProvider
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SearchResult(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val type: String // "CONTENT", "PROJECT", "JOURNEY", "AI_TOOL"
)

class ContentRepository {

    private val _contents = MutableStateFlow(MockDataProvider.contents)
    val contents: StateFlow<List<ContentItem>> = _contents.asStateFlow()

    private val _projects = MutableStateFlow(MockDataProvider.projects)
    val projects: StateFlow<List<ProjectItem>> = _projects.asStateFlow()

    private val _journeyPosts = MutableStateFlow(MockDataProvider.journeyPosts)
    val journeyPosts: StateFlow<List<JourneyItem>> = _journeyPosts.asStateFlow()

    val profile = UserProfile()

    fun getContentById(id: String): ContentItem? {
        return _contents.value.find { it.id == id }
    }

    fun getProjectById(id: String): ProjectItem? {
        return _projects.value.find { it.id == id }
    }

    fun getJourneyById(id: String): JourneyItem? {
        return _journeyPosts.value.find { it.id == id }
    }

    fun filterContent(
        selectedCategory: String,
        selectedLevel: ContentLevel,
        selectedType: ContentType?,
        query: String = ""
    ): List<ContentItem> {
        return _contents.value.filter { item ->
            val matchesCat = selectedCategory == "همه موضوعات" || item.category.equals(selectedCategory, ignoreCase = true)
            val matchesLevel = selectedLevel == ContentLevel.ALL || item.level == selectedLevel
            val matchesType = selectedType == null || item.type == selectedType
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.excerpt.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)

            matchesCat && matchesLevel && matchesType && matchesQuery
        }
    }

    fun filterProjects(status: ProjectStatus?): List<ProjectItem> {
        return if (status == null) {
            _projects.value
        } else {
            _projects.value.filter { it.status == status }
        }
    }

    fun filterJourney(category: JourneyCategory): List<JourneyItem> {
        return if (category == JourneyCategory.ALL) {
            _journeyPosts.value
        } else {
            _journeyPosts.value.filter { it.category == category }
        }
    }

    fun searchGlobal(query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()
        val trimmed = query.trim()
        val results = mutableListOf<SearchResult>()

        // Search Content
        _contents.value.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
            it.excerpt.contains(trimmed, ignoreCase = true) ||
            it.category.contains(trimmed, ignoreCase = true)
        }.forEach {
            results.add(
                SearchResult(
                    id = it.id,
                    title = it.title,
                    subtitle = it.excerpt,
                    category = it.category,
                    type = "CONTENT"
                )
            )
        }

        // Search Projects
        _projects.value.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
            it.description.contains(trimmed, ignoreCase = true) ||
            it.technologies.any { tech -> tech.contains(trimmed, ignoreCase = true) }
        }.forEach {
            results.add(
                SearchResult(
                    id = it.id,
                    title = it.title,
                    subtitle = it.description,
                    category = it.status.titleFa,
                    type = "PROJECT"
                )
            )
        }

        // Search Journey
        _journeyPosts.value.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
            it.story.contains(trimmed, ignoreCase = true) ||
            it.lesson.contains(trimmed, ignoreCase = true)
        }.forEach {
            results.add(
                SearchResult(
                    id = it.id,
                    title = it.title,
                    subtitle = it.lesson,
                    category = it.category.titleFa,
                    type = "JOURNEY"
                )
            )
        }

        return results
    }
}
