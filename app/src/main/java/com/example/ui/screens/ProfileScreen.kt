package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ProjectItem
import com.example.data.model.UserProfile
import com.example.ui.components.ProjectCard
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
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    profile: UserProfile,
    projects: List<ProjectItem>,
    bookmarkedIds: Set<String>,
    onToggleBookmark: (id: String, type: String, title: String, category: String, subtitle: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedProjectForDetail by remember { mutableStateOf<ProjectItem?>(null) }
    var showContactDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        // 1. Profile Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceDark)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Portrait Image
                    Box(
                        modifier = Modifier
                            .size(96.dp)
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

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = profile.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldLight,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = profile.location,
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Bio
                    Text(
                        text = profile.bio,
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Philosophy Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceVariantDark)
                            .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "فلسفه من (Philosophy):",
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = profile.philosophy,
                                color = TextWhite,
                                fontSize = 12.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Social & Contact Links Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SocialIconButton(
                            label = "اینستاگرام",
                            onClick = {
                                openUrl(context, profile.instagramUrl)
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SocialIconButton(
                            label = "لینکدین",
                            onClick = {
                                openUrl(context, profile.linkedinUrl)
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SocialIconButton(
                            label = "گیت‌هاب",
                            onClick = {
                                openUrl(context, profile.githubUrl)
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        SocialIconButton(
                            label = "ایمیل",
                            onClick = {
                                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${profile.email}")
                                }
                                try {
                                    context.startActivity(emailIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, profile.email, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { showContactDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = BackgroundDark
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text(text = "بیایید در ارتباط باشیم", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 2. Skills & Expertise Tags
        item {
            SectionHeader(
                title = "مهارت‌ها و حوزه تخصص",
                subtitle = "تمرکز اصلی مهندسی و هوش مصنوعی"
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                profile.expertise.forEach { skill ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceElevated)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = skill,
                            color = TechCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // 3. Technical Projects Showcase
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionHeader(
                title = "پروژه‌های مهندسی و AI",
                subtitle = "سیستم‌ها و ابزارهایی که ساخته‌ام"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                projects.forEach { project ->
                    ProjectCard(
                        project = project,
                        isBookmarked = bookmarkedIds.contains(project.id),
                        onBookmarkClick = {
                            onToggleBookmark(
                                project.id,
                                "PROJECT",
                                project.title,
                                project.status.titleFa,
                                project.description
                            )
                        },
                        onClick = { selectedProjectForDetail = project }
                    )
                }
            }
        }

        // 4. Career Experience
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionHeader(
                title = "تجربه‌های کاری و سوابق",
                subtitle = "مسیر حرفه‌ای در صنعت و مهندسی"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                profile.experiences.forEach { exp ->
                    ExperienceCard(exp)
                }
            }
        }

        // 5. Education
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionHeader(
                title = "تحصیلات دانشگاهی",
                subtitle = "پایه‌های آکادمیک مهندسی"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                profile.education.forEach { edu ->
                    EducationCard(edu)
                }
            }
        }
    }

    // Project Details Dialog
    if (selectedProjectForDetail != null) {
        val proj = selectedProjectForDetail!!
        AlertDialog(
            onDismissRequest = { selectedProjectForDetail = null },
            title = {
                Text(
                    text = proj.title,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(text = "مسئله (Problem):", color = GoldLight, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = proj.problem, color = Color(0xFFCBD5E1), fontSize = 12.sp, lineHeight = 18.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "راه‌حل (Solution):", color = TechCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = proj.solution, color = Color(0xFFCBD5E1), fontSize = 12.sp, lineHeight = 18.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "معماری فنی (Architecture):", color = GoldLight, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = proj.architecture, color = Color(0xFFCBD5E1), fontSize = 12.sp, lineHeight = 18.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "چیزی که یاد گرفتم (Lessons):", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = proj.lessons, color = TextWhite, fontSize = 12.sp, lineHeight = 18.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (proj.githubUrl != null) {
                            openUrl(context, proj.githubUrl)
                        } else {
                            Toast.makeText(context, "ریپو در دسترس است", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = BackgroundDark)
                ) {
                    Text("مشاهده گیت‌هاب")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedProjectForDetail = null }) {
                    Text("بستن", color = TextMuted)
                }
            },
            containerColor = SurfaceDark
        )
    }

    // Direct Contact Dialog
    if (showContactDialog) {
        var contactName by remember { mutableStateOf("") }
        var contactMsg by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showContactDialog = false },
            title = {
                Text(
                    text = "ارتباط مستقیم با محمد",
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "برای پیشنهادات همکاری، مشاوره یا گپ دوستانه پیام خود را بنویسید:",
                        color = TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        placeholder = { Text("نام و ایمیل شما...", color = TextDim, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contactMsg,
                        onValueChange = { contactMsg = it },
                        placeholder = { Text("پیام یا ایده شما...", color = TextDim, fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (contactMsg.isNotBlank()) {
                            Toast.makeText(context, "پیام شما ارسال شد! محمد به زودی پاسخ خواهد داد.", Toast.LENGTH_LONG).show()
                            showContactDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = BackgroundDark)
                ) {
                    Text("ارسال پیام")
                }
            },
            dismissButton = {
                TextButton(onClick = { showContactDialog = false }) {
                    Text("انصراف", color = TextMuted)
                }
            },
            containerColor = SurfaceDark
        )
    }
}

@Composable
fun SocialIconButton(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceElevated)
            .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = GoldLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ExperienceCard(exp: com.example.data.model.WorkExperience) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceDark)
            .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exp.role,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 14.sp
                )
                Text(
                    text = exp.period,
                    color = GoldLight,
                    fontSize = 11.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = exp.company,
                color = TechCyan,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = exp.description,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            exp.achievements.forEach { ach ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "• ", color = GoldPrimary)
                    Text(text = ach, color = Color(0xFFCBD5E1), fontSize = 11.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

@Composable
fun EducationCard(edu: com.example.data.model.EducationItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceDark)
            .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SurfaceElevated),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "${edu.degree} — ${edu.field}",
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 13.sp
                )
                Text(
                    text = "${edu.university} • ${edu.year}",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }
    }
}

private fun openUrl(context: android.content.Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
    }
}
