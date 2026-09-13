package com.example

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.local.AppDatabase
import com.example.data.model.ContentItem
import com.example.data.model.ProjectItem
import com.example.data.repository.AiAssistantRepository
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.ContentRepository
import com.example.ui.components.MohammadBottomNav
import com.example.ui.components.MohammadTopBar
import com.example.ui.components.NavDestination
import com.example.ui.screens.AiLabScreen
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ContentDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.JourneyScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MohammadBrandTheme
import kotlinx.coroutines.launch

enum class AppView {
    SPLASH,
    ONBOARDING,
    MAIN,
    CONTENT_DETAIL,
    BOOKMARKS,
    SEARCH
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val bookmarkRepository = BookmarkRepository(database.bookmarkDao())
        val contentRepository = ContentRepository()
        val aiRepository = AiAssistantRepository()

        val sharedPrefs = getSharedPreferences("mohammad_app_prefs", Context.MODE_PRIVATE)
        val hasCompletedOnboarding = sharedPrefs.getBoolean("has_completed_onboarding", false)

        setContent {
            MohammadBrandTheme {
                MohammadAppRoot(
                    bookmarkRepository = bookmarkRepository,
                    contentRepository = contentRepository,
                    aiRepository = aiRepository,
                    initialOnboardingDone = hasCompletedOnboarding,
                    onSaveOnboardingDone = {
                        sharedPrefs.edit().putBoolean("has_completed_onboarding", true).apply()
                    }
                )
            }
        }
    }
}

@Composable
fun MohammadAppRoot(
    bookmarkRepository: BookmarkRepository,
    contentRepository: ContentRepository,
    aiRepository: AiAssistantRepository,
    initialOnboardingDone: Boolean,
    onSaveOnboardingDone: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var currentView by remember { mutableStateOf(AppView.SPLASH) }
    var currentNavDestination by remember { mutableStateOf(NavDestination.HOME) }
    var activeContentItem by remember { mutableStateOf<ContentItem?>(null) }

    val bookmarks by bookmarkRepository.bookmarks.collectAsState(initial = emptyList())
    val bookmarkedIds = remember(bookmarks) { bookmarks.map { it.id }.toSet() }

    val contents by contentRepository.contents.collectAsState()
    val projects by contentRepository.projects.collectAsState()
    val journeyPosts by contentRepository.journeyPosts.collectAsState()
    val latestJourney = remember(journeyPosts) { journeyPosts.firstOrNull() }

    // Intercept back button when not in main home
    BackHandler(enabled = currentView != AppView.SPLASH) {
        when (currentView) {
            AppView.CONTENT_DETAIL -> currentView = AppView.MAIN
            AppView.BOOKMARKS -> currentView = AppView.MAIN
            AppView.SEARCH -> currentView = AppView.MAIN
            AppView.ONBOARDING -> currentView = AppView.MAIN
            AppView.MAIN -> {
                if (currentNavDestination != NavDestination.HOME) {
                    currentNavDestination = NavDestination.HOME
                }
            }
            AppView.SPLASH -> {}
        }
    }

    when (currentView) {
        AppView.SPLASH -> {
            SplashScreen(
                onFinish = {
                    currentView = if (initialOnboardingDone) AppView.MAIN else AppView.ONBOARDING
                }
            )
        }

        AppView.ONBOARDING -> {
            OnboardingScreen(
                onComplete = {
                    onSaveOnboardingDone()
                    currentView = AppView.MAIN
                }
            )
        }

        AppView.CONTENT_DETAIL -> {
            val item = activeContentItem
            if (item != null) {
                ContentDetailScreen(
                    item = item,
                    isBookmarked = bookmarkedIds.contains(item.id),
                    onToggleBookmark = {
                        coroutineScope.launch {
                            bookmarkRepository.toggleBookmark(
                                id = item.id,
                                type = "CONTENT",
                                title = item.title,
                                category = item.category,
                                subtitle = item.excerpt,
                                currentlyBookmarked = bookmarkedIds.contains(item.id)
                            )
                        }
                    },
                    onBack = { currentView = AppView.MAIN },
                    onRelatedClick = { relatedId ->
                        val related = contentRepository.getContentById(relatedId)
                        if (related != null) {
                            activeContentItem = related
                        }
                    }
                )
            } else {
                currentView = AppView.MAIN
            }
        }

        AppView.BOOKMARKS -> {
            BookmarksScreen(
                bookmarks = bookmarks,
                onRemoveBookmark = { id ->
                    coroutineScope.launch {
                        bookmarkRepository.removeBookmark(id)
                    }
                },
                onItemClick = { entity ->
                    when (entity.type) {
                        "CONTENT" -> {
                            val content = contentRepository.getContentById(entity.id)
                            if (content != null) {
                                activeContentItem = content
                                currentView = AppView.CONTENT_DETAIL
                            }
                        }
                        "PROJECT" -> {
                            currentNavDestination = NavDestination.PROFILE
                            currentView = AppView.MAIN
                        }
                        "JOURNEY" -> {
                            currentNavDestination = NavDestination.JOURNEY
                            currentView = AppView.MAIN
                        }
                    }
                },
                onBack = { currentView = AppView.MAIN }
            )
        }

        AppView.SEARCH -> {
            SearchScreen(
                repository = contentRepository,
                onResultClick = { result ->
                    when (result.type) {
                        "CONTENT" -> {
                            val content = contentRepository.getContentById(result.id)
                            if (content != null) {
                                activeContentItem = content
                                currentView = AppView.CONTENT_DETAIL
                            }
                        }
                        "PROJECT" -> {
                            currentNavDestination = NavDestination.PROFILE
                            currentView = AppView.MAIN
                        }
                        "JOURNEY" -> {
                            currentNavDestination = NavDestination.JOURNEY
                            currentView = AppView.MAIN
                        }
                    }
                },
                onBack = { currentView = AppView.MAIN }
            )
        }

        AppView.MAIN -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    MohammadTopBar(
                        onSearchClick = { currentView = AppView.SEARCH },
                        onBookmarksClick = { currentView = AppView.BOOKMARKS },
                        bookmarkCount = bookmarks.size
                    )
                },
                bottomBar = {
                    MohammadBottomNav(
                        currentDestination = currentNavDestination,
                        onNavigate = { currentNavDestination = it }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentNavDestination) {
                        NavDestination.HOME -> {
                            HomeScreen(
                                profile = contentRepository.profile,
                                latestContents = contents,
                                engineeringContents = contents.filter { it.isEngineeringSpecial },
                                latestJourney = latestJourney,
                                bookmarkedIds = bookmarkedIds,
                                onToggleBookmark = { id, type, title, category, subtitle ->
                                    coroutineScope.launch {
                                        bookmarkRepository.toggleBookmark(
                                            id = id,
                                            type = type,
                                            title = title,
                                            category = category,
                                            subtitle = subtitle,
                                            currentlyBookmarked = bookmarkedIds.contains(id)
                                        )
                                    }
                                },
                                onContentClick = { item ->
                                    activeContentItem = item
                                    currentView = AppView.CONTENT_DETAIL
                                },
                                onNavigateTab = { currentNavDestination = it },
                                onOpenContact = { currentNavDestination = NavDestination.PROFILE }
                            )
                        }

                        NavDestination.LEARN -> {
                            LearnScreen(
                                contents = contents,
                                bookmarkedIds = bookmarkedIds,
                                onToggleBookmark = { id, type, title, category, subtitle ->
                                    coroutineScope.launch {
                                        bookmarkRepository.toggleBookmark(
                                            id = id,
                                            type = type,
                                            title = title,
                                            category = category,
                                            subtitle = subtitle,
                                            currentlyBookmarked = bookmarkedIds.contains(id)
                                        )
                                    }
                                },
                                onContentClick = { item ->
                                    activeContentItem = item
                                    currentView = AppView.CONTENT_DETAIL
                                }
                            )
                        }

                        NavDestination.AI_LAB -> {
                            AiLabScreen(aiRepository = aiRepository)
                        }

                        NavDestination.JOURNEY -> {
                            JourneyScreen(
                                journeyPosts = journeyPosts,
                                bookmarkedIds = bookmarkedIds,
                                onToggleBookmark = { id, type, title, category, subtitle ->
                                    coroutineScope.launch {
                                        bookmarkRepository.toggleBookmark(
                                            id = id,
                                            type = type,
                                            title = title,
                                            category = category,
                                            subtitle = subtitle,
                                            currentlyBookmarked = bookmarkedIds.contains(id)
                                        )
                                    }
                                }
                            )
                        }

                        NavDestination.PROFILE -> {
                            ProfileScreen(
                                profile = contentRepository.profile,
                                projects = projects,
                                bookmarkedIds = bookmarkedIds,
                                onToggleBookmark = { id, type, title, category, subtitle ->
                                    coroutineScope.launch {
                                        bookmarkRepository.toggleBookmark(
                                            id = id,
                                            type = type,
                                            title = title,
                                            category = category,
                                            subtitle = subtitle,
                                            currentlyBookmarked = bookmarkedIds.contains(id)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
