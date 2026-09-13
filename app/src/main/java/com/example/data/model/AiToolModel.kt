package com.example.data.model

enum class AiToolType(
    val titleFa: String,
    val subtitleFa: String,
    val inputPlaceholder: String,
    val icon: String,
    val defaultExamples: List<String>
) {
    PROMPT_BUILDER(
        titleFa = "سازنده پرامپت حرفه‌ای",
        subtitleFa = "تبدیل ایده خام شما به پرامپت مهندسی‌شده با ساختار نقش و خروجی دقیق",
        inputPlaceholder = "مثال: می‌خواهم برای محصولم پست اینستاگرام بسازم...",
        icon = "psychology",
        defaultExamples = listOf(
            "می‌خواهم برای محصولم یک پست معرفی اینستاگرام بنویسم",
            "نیاز به پرامپت برای تحلیل نمودار تک‌خطی برق صنعتی دارم",
            "پرامپتی برای تبدیل متن مقاله مهندسی به ریلز اینستاگرام"
        )
    ),
    EXPLAIN_BEGINNER(
        titleFa = "توضیح به زبان ساده",
        subtitleFa = "ساده‌سازی مفاهیم پیچیده هوش مصنوعی و مهندسی با مثال‌های ملموس",
        inputPlaceholder = "مثال: ترنسفورمر (Transformer) چیست؟",
        icon = "auto_stories",
        defaultExamples = listOf(
            "معماری Transformer در یادگیری عمیق چگونه کار می‌کند؟",
            "سیستم‌های کنترل حلقه بسته و فیدبک چیست؟",
            "مفهوم RAG در مدل‌های زبانی به چه معناست؟"
        )
    ),
    ENGINEERING_ASSISTANT(
        titleFa = "دستیار تخصصی مهندسی",
        subtitleFa = "پاسخ ساختاریافته به مسائل مهندسی برق، کنترل و صنایع نفت و گاز",
        inputPlaceholder = "مثال: نحوه محاسبه افت ولتاژ در کابل‌های فشار متوسط...",
        icon = "precision_manufacturing",
        defaultExamples = listOf(
            "روش اصولی کالیبراسیون کنترل‌کننده PID در لوپ‌های فشار گاز",
            "تفاوت موتورهای سنکرون و آسنکرون در پمپ‌های فرآیندی نفت",
            "چگونه یک سیستم RAG برای بررسی استانداردهای API و IPS بسازیم؟"
        )
    ),
    CONTENT_IDEA_GENERATOR(
        titleFa = "ایده‌ساز محتوا و هوک",
        subtitleFa = "تولید ۱۰ ایده محتوا، هوک‌های جذاب ریلز و CTAهای مؤثر در حوزه کاری شما",
        inputPlaceholder = "مثال: اتوماسیون صنعتی و ابزار دقیق یا آموزش هوش مصنوعی...",
        icon = "lightbulb",
        defaultExamples = listOf(
            "آموزش کاربردی هوش مصنوعی برای مهندسان برق",
            "اتوماسیون کارهای روتین اداری با AI Agents",
            "بررسی پروژه‌های نفت و گاز با ابزارهای نوین"
        )
    ),
    LEARN_SOMETHING(
        titleFa = "نقشه یادگیری سریع",
        subtitleFa = "ایجاد Mini Learning Path ۴ مرحله‌ای: مقدماتی، متوسط، پروژه واقعی و منابع برتر",
        inputPlaceholder = "مثال: ساخت AI Agent با پایتون، مهندسی اتوماسیون، بهینه‌سازی فرآیند...",
        icon = "school",
        defaultExamples = listOf(
            "یادگیری ساخت AI Agents از صفر",
            "اصول برق صنعتی و طراحی تابلو",
            "معماری RAG برای اسناد سازمانی"
        )
    )
}

data class AiToolResult(
    val toolType: AiToolType,
    val inputQuery: String,
    val resultText: String,
    val timestamp: Long = System.currentTimeMillis()
)
