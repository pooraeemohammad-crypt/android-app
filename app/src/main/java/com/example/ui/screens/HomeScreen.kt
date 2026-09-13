package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ContentItem
import com.example.data.model.JourneyItem
import com.example.data.model.UserProfile
import com.example.ui.components.HorizontalContentCard
import com.example.ui.components.NavDestination
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldMuted
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.SurfaceVariantDark
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechCyanMuted
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun HomeScreen(
    profile: UserProfile,
    latestContents: List<ContentItem>,
    engineeringContents: List<ContentItem>,
    latestJourney: JourneyItem?,
    bookmarkedIds: Set<String>,
    onToggleBookmark: (id: String, type: String, title: String, category: String, subtitle: String) -> Unit,
    onContentClick: (ContentItem) -> Unit,
    onNavigateTab: (NavDestination) -> Unit,
    onOpenContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 1. Personal Header Greeting
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "سلام 👋 من محمدم.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "مهندس برق | AI Builder | در حال ساخت آینده خودم",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GoldLight,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }

        // 2. Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF161D2B), Color(0xFF0F141F))
                        )
                    )
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Mohammad Portrait
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .border(2.dp, GoldPrimary, CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_mohammad_hero),
                                contentDescription = "محمد",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "محمد",
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 16.sp
                            )
                            Text(
                                text = profile.title,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quote
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceDark)
                            .border(1.dp, GoldPrimary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "« ${profile.quote} »",
                            color = TextWhite,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 24.sp,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // CTAs Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onNavigateTab(NavDestination.JOURNEY) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = BackgroundDark
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Text(
                                text = "مشاهده مسیر من",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { onNavigateTab(NavDestination.LEARN) },
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Text(
                                text = "شروع یادگیری",
                                color = GoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // 3. Latest Content (جدیدترین محتوا)
        item {
            SectionHeader(
                title = "جدیدترین محتوا",
                subtitle = "مقالات، آموزش‌ها، ریلز و یادداشت‌های اخیر",
                actionText = "مشاهده همه",
                onActionClick = { onNavigateTab(NavDestination.LEARN) }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(latestContents) { item ->
                    HorizontalContentCard(
                        item = item,
                        isBookmarked = bookmarkedIds.contains(item.id),
                        onBookmarkClick = {
                            onToggleBookmark(
                                item.id,
                                "CONTENT",
                                item.title,
                                item.category,
                                item.excerpt
                            )
                        },
                        onClick = { onContentClick(item) }
                    )
                }
            }
        }

        // 4. AI × Engineering Highlight Section
        item {
            Spacer(modifier = Modifier.height(18.dp))
            SectionHeader(
                title = "AI × Engineering",
                subtitle = "تلفیق هوش مصنوعی در پروژه‌های برق، کنترل و نفت و گاز",
                actionText = "مشاهده پروژه‌ها",
                onActionClick = { onNavigateTab(NavDestination.LEARN) }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                engineeringContents.take(3).forEach { item ->
                    EngineeringHighlightCard(
                        item = item,
                        onClick = { onContentClick(item) }
                    )
                }
            }
        }

        // 5. AI Lab Quick Access Banner
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF1E2638), Color(0xFF131926))
                        )
                    )
                    .border(1.dp, TechCyan.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .clickable { onNavigateTab(NavDestination.AI_LAB) }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(TechCyanMuted)
                                .border(1.dp, TechCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = TechCyan,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "آزمایشگاه هوش مصنوعی (AI Lab)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "۵ ابزار رایگان: پرامپت‌ساز، توضیح ساده، دستیار مهندسی...",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "ورود به ابزارها",
                        tint = GoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // 6. Journey Snapshot (Timeline کوتاه)
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionHeader(
                title = "مسیر ساختن و یادگیری (Journey)",
                subtitle = "گزارش وضعیت و Build in Public",
                actionText = "ادامه مسیر",
                onActionClick = { onNavigateTab(NavDestination.JOURNEY) }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                JourneyQuickFactRow(
                    icon = Icons.Default.CheckCircle,
                    iconTint = StatusSuccessColor,
                    label = "امروز چه چیزی ساختم؟",
                    value = "تست موفقیت‌آمیز استخراج متریال با Vision Agent در مدارک P&ID"
                )
                JourneyQuickFactRow(
                    icon = Icons.Default.Lightbulb,
                    iconTint = GoldPrimary,
                    label = "این هفته چه چیزی یاد گرفتم؟",
                    value = "جداسازی لایه استدلال LLM از محاسبات قطعی کابل با Function Calling"
                )
                JourneyQuickFactRow(
                    icon = Icons.Default.Engineering,
                    iconTint = TechCyan,
                    label = "پروژه فعلی:",
                    value = "AI Engineering Document Reviewer نسخه بتا"
                )
                JourneyQuickFactRow(
                    icon = Icons.Default.Warning,
                    iconTint = Color(0xFFF59E0B),
                    label = "چالش فعلی:",
                    value = "کیفیت اسکن‌های قدیمی در استخراج متون مهندسی"
                )
                JourneyQuickFactRow(
                    icon = Icons.Default.Flag,
                    iconTint = GoldLight,
                    label = "هدف بعدی:",
                    value = "ایجاد دیتابیس جامع استانداردهای IPS با RAG ترکیبی"
                )
            }
        }

        // 7. Community Follow CTA
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceDark)
                    .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "مسیر را با من ادامه بده",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "می‌خواهی در مورد پروژه‌ها، آموزش‌ها یا همکاری با هم صحبت کنیم؟",
                        color = TextMuted,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onOpenContact,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = BackgroundDark
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "ارتباط مستقیم با محمد", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

private val StatusSuccessColor = Color(0xFF10B981)

@Composable
fun EngineeringHighlightCard(
    item: ContentItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(GoldMuted),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Engineering,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.excerpt,
                color = TextMuted,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun JourneyQuickFactRow(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceElevated)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = value,
            color = TextWhite,
            fontSize = 11.sp,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}
