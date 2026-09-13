package com.example.data.model

data class WorkExperience(
    val role: String,
    val company: String,
    val period: String,
    val description: String,
    val achievements: List<String>
)

data class EducationItem(
    val degree: String,
    val field: String,
    val university: String,
    val year: String,
    val focus: String
)

data class UserProfile(
    val name: String = "محمد",
    val title: String = "Electrical Engineer × AI Builder",
    val tagline: String = "AI × Engineering × Growth",
    val bio: String = "مهندس برق، علاقه‌مند به اتوماسیون، ایجنت‌های هوش مصنوعی و کاربرد هوش مصنوعی در صنایع نفت، گاز و سیستم‌های کنترل. در حال ساختن، یادگیری و مستندسازی مسیر.",
    val philosophy: String = "من متخصص همه‌چیز نیستم؛ در حال یادگیری، ساختن و به اشتراک گذاشتن چیزهایی هستم که واقعاً امتحان می‌کنم. Build → Learn → Share → Improve",
    val quote: String = "AI فقط یک ابزار نیست؛ یک اهرم برای ساختن نسخه بهتر ماست.",
    val location: String = "ایران — تهران",
    val email: String = "pooraeemohammad@gmail.com",
    val instagramUrl: String = "https://instagram.com/mohammad_builder",
    val linkedinUrl: String = "https://linkedin.com/in/mohammad-engineer",
    val githubUrl: String = "https://github.com/mohammad-builder",
    val websiteUrl: String = "https://mohammad.app",
    val expertise: List<String> = listOf(
        "Electrical Engineering",
        "Control Engineering",
        "Oil & Gas Systems",
        "AI & Large Language Models",
        "Industrial Automation",
        "Autonomous AI Agents",
        "RAG & Semantic Search",
        "Python & System Architecture"
    ),
    val experiences: List<WorkExperience> = listOf(
        WorkExperience(
            role = "Senior AI & Automation Solutions Architect",
            company = "پروژه‌های مهندسی و صنعتی",
            period = "۱۴۰۲ — اکنون",
            description = "توسعه ایجنت‌های هوشمند برای تحلیل مدارک فنی، خودکارسازی نقشه‌خوانی مهندسی و پیاده‌سازی RAG روی استانداردهای فنی.",
            achievements = listOf(
                "کاهش ۷۰٪ زمان بازبینی اسناد فنی و P&ID با استفاده از بینایی ماشین و LLM",
                "طراحی پایپ‌لاین اتوماسیون داده‌های مهندسی با ایجنت‌های خودمختار"
            )
        ),
        WorkExperience(
            role = "Electrical & Control Systems Engineer",
            company = "صنایع نفت، گاز و پتروشیمی",
            period = "۱۳۹۹ — ۱۴۰۲",
            description = "طراحی و نظارت بر سیستم‌های کنترل فرآیند، لوپ‌های ابزار دقیق و تابلوهای توزیع برق فشار ضعیف و متوسط.",
            achievements = listOf(
                "بهینه‌سازی تنظیمات کنترلرهای PLC/DCS در واحدهای فرآیندی",
                "نظارت بر تطابق پروژه‌ها با استانداردهای بین‌المللی API و IEC"
            )
        )
    ),
    val education: List<EducationItem> = listOf(
        EducationItem(
            degree = "کارشناسی ارشد",
            field = "مهندسی برق — سیستم‌های کنترل",
            university = "دانشگاه سراسری",
            year = "۱۳۹۹",
            focus = "کنترل بهینه و تحلیل داده‌های سیستم‌های دینامیکی"
        ),
        EducationItem(
            degree = "کارشناسی",
            field = "مهندسی برق — قدرت و کنترل",
            university = "دانشگاه سراسری",
            year = "۱۳۹۶",
            focus = "طراحی شبکه‌های قدرت و اتوماسیون صنعتی"
        )
    )
)
