package com.example.data.model

object MockDataProvider {

    val categories = listOf(
        "همه موضوعات",
        "AI Basics",
        "ChatGPT",
        "Prompt Engineering",
        "AI Agents",
        "Automation",
        "Engineering AI",
        "Electrical Engineering",
        "Control",
        "Oil & Gas",
        "Productivity"
    )

    val contents: List<ContentItem> = listOf(
        ContentItem(
            id = "c1",
            title = "AI برای بررسی مدارک مهندسی: خودکارسازی تطبیق نقشه‌ها و دیتاشیت‌ها",
            category = "Engineering AI",
            type = ContentType.ARTICLE,
            level = ContentLevel.INTERMEDIATE,
            durationText = "۸ دقیقه مطالعه",
            excerpt = "چگونه با استفاده از Vision LLMs و تکنیک RAG، زمان بازبینی مدارک مهندسی و نقشه‌های P&ID را تا ۷۰٪ کاهش دهیم؟",
            fullContent = """
بررسی مدارک مهندسی (MDR - Master Document Register) در پروژه‌های نفت و گاز و برق، یکی از وقت‌گیرترین مراحل مهندسی است. یک مهندس باید خط به خط مشخصات تجهیزات، استانداردهای متریال و جداول دیتاشیت را با مدارک بالادستی (Design Basis) تطبیق دهد.

در این مقاله، متدولوژی عملی استفاده از مدل‌های زبانی بینایی‌محور (Vision LLM) به همراه ساختار RAG روی استانداردهای بین‌المللی مانند API 610 و IEC 60034 را گام به گام شرح داده‌ام.

مراحل اصلی پیاده‌سازی:
۱. استخراج المان‌های برداری و جداول دیتاشیت با OCR تخصصی اسناد فنی.
۲. تبدیل متن استانداردها به بردارهای معنایی و ذخیره در پایگاه داده وکتوری.
۳. تعریف Agent اعتبارسنجی با پرامپت مهندسی دقیق برای یافتن تناقض‌های فشار، دما و جنس بدنه.
            """.trimIndent(),
            codeSnippet = """
# نمونه اعتبارسنجی دیتاشیت با Python و LangChain
from langchain.chains import RetrievalQA
from langchain_community.vectorstores import Chroma

def verify_engineering_spec(datasheet_text, standard_retriever):
    prompt_template = \"\"\"
    شما یک مهندس ارشد کنترل کیفیت در پروژه نفت و گاز هستید.
    دیتاشیت زیر را بررسی کنید و هرگونه مغایرت با استانداردهای مرجع را گزارش نمایید:
    {datasheet_text}
    \"\"\"
    qa_chain = RetrievalQA.from_chain_type(
        llm=model,
        retriever=standard_retriever
    )
    return qa_chain.run(datasheet_text)
            """.trimIndent(),
            codeLanguage = "python",
            publishDate = "۱۴۰۳/۰۴/۱۵",
            relatedIds = listOf("c2", "c3"),
            instagramReelUrl = "https://instagram.com/reel/example1",
            isEngineeringSpecial = true
        ),
        ContentItem(
            id = "c2",
            title = "ساخت اولین AI Agent تخصصی برای مهندسان برق و کنترل",
            category = "AI Agents",
            type = ContentType.TUTORIAL,
            level = ContentLevel.ADVANCED,
            durationText = "۱۲ دقیقه مطالعه",
            excerpt = "معماری ReAct و Function Calling برای اتصال هوش مصنوعی به ابزارهای محاسباتی مهندسی نظیر محاسبه سایز کابل و اتصال کوتاه.",
            fullContent = """
هوش مصنوعی معمولی نمی‌تواند محاسبات دقیق مهندسی برق را تضمین کند؛ چون مدل‌های زبانی ذاتاً تولیدکننده احتمالاتی هستند. راه‌حل مهندسی چیست؟ اتصال Agent به Toolهای قطعی و محاسباتی با ابزار Function Calling.

در این آموزش، یک ایجنت خودمختار می‌سازیم که:
- سؤال مهندس را تحلیل می‌کند.
- داده‌های ورودی (جریان بار، طول کابل، ضریب قدرت) را استخراج می‌کند.
- تابع پایتونی سایز کابل بر اساس استاندارد IEC 60502 را فراخوانی می‌کند.
- خروجی تایید شده و محاسبه شده را تحویل می‌دهد.
            """.trimIndent(),
            codeSnippet = """
@tool
def calculate_cable_cross_section(current_amp: float, length_m: float, max_drop_percent: float = 3.0):
    \"\"\"محاسبه دقیق سطح مقطع کابل مسی طبق افت ولتاژ استاندارد\"\"\"
    voltage = 400.0  # Three phase 400V
    copper_rho = 0.0175  # ohm*mm2/m
    allowable_drop_v = (max_drop_percent / 100.0) * voltage
    required_s = (1.732 * length_m * current_amp * copper_rho) / allowable_drop_v
    return f"حداقل سطح مقطع استاندارد: {round(required_s, 2)} mm²"
            """.trimIndent(),
            codeLanguage = "python",
            publishDate = "۱۴۰۳/۰۴/۲۲",
            relatedIds = listOf("c1", "c5"),
            instagramReelUrl = "https://instagram.com/reel/example2",
            isEngineeringSpecial = true
        ),
        ContentItem(
            id = "c3",
            title = "RAG برای استانداردهای صنعتی: دسترسی سریع به کدهای API و IEC",
            category = "Engineering AI",
            type = ContentType.ARTICLE,
            level = ContentLevel.INTERMEDIATE,
            durationText = "۶ دقیقه مطالعه",
            excerpt = "چطور یک مخزن جستجوی معنایی محلی از هزاران صفحه استاندارد صنعتی برای کل تیم مهندسی ایجاد کنیم؟",
            fullContent = """
مهندسان روزانه ساعت‌ها وقت صرف جستجوی بندهای خاص در استانداردهای قطور چندصد صفحه‌ای نظیر API 520 یا IEC 61508 (ایمنی عملکردی) می‌کنند.

سیستم RAG (Retrieval-Augmented Generation) به عنوان دستیار هوشمند، نه تنها بند مربوطه را پیدا می‌کند، بلکه آن را برای مسئله خاص پروژه شما تفسیر و فرمول مربوطه را پیشنهاد می‌دهد.
            """.trimIndent(),
            publishDate = "۱۴۰۳/۰۵/۰۵",
            relatedIds = listOf("c1"),
            isEngineeringSpecial = true
        ),
        ContentItem(
            id = "c4",
            title = "پایه و اساس Prompt Engineering: فرمول ۵ مرحله‌ای برای نتیجه تمیز",
            category = "Prompt Engineering",
            type = ContentType.MINI_COURSE,
            level = ContentLevel.BEGINNER,
            durationText = "۵ دقیقه مطالعه",
            excerpt = "یک پرامپت مهندسی چطور نوشته می‌شود؟ تکنیک Role + Context + Instruction + Constraint + Output Format.",
            fullContent = """
خیلی‌ها فکر می‌کنند پرامپت نوشتن یعنی یک جمله عامیانه به هوش مصنوعی تحویل دادن و منتظر معجزه بودن! در دنیای مهندسی، پرامپت مانند Specification Sheet است.

فرمول ۵ مرحله‌ای محمد برای پرامپت‌های سطح بالا:
۱. Role (نقش تخصصی مشخص)
۲. Context (بستر پروژه و متغیرها)
۳. Instructions (گام‌های صریح و دستورالعمل)
۴. Constraints (محدودیت‌ها و مواردی که نباید انجام شود)
۵. Format (فرمت خروجی، جدول، کد، لیست و ساختار)
            """.trimIndent(),
            publishDate = "۱۴۰۳/۰۵/۱۸",
            relatedIds = listOf("c5", "c6")
        ),
        ContentItem(
            id = "c5",
            title = "اتوماسیون گزارش‌های مهندسی با هوش مصنوعی و Python",
            category = "Automation",
            type = ContentType.PROJECT,
            level = ContentLevel.INTERMEDIATE,
            durationText = "۱۰ دقیقه مطالعه",
            excerpt = "ساخت پایپ‌لاین تولید خودکار گزارش‌های روزانه پیشرفت پروژه (DPR) از روی لاگ‌های سایت و پیام‌های تلگرام/بله.",
            fullContent = """
گزارش‌های روزانه کارگاهی و پیشرفت مهندسی معمولاً از پیام‌ها، جداول اکسل نامنظم و یادداشت‌های دست‌نویس تشکیل شده‌اند. با ساخت یک پایپ‌لاین خودکار، این داده‌ها وارد یک اسکریپت شده و گزارش نهایی رسمی در قالب PDF و ورد خروجی داده می‌شود.
            """.trimIndent(),
            publishDate = "۱۴۰۳/۰۶/۰۱",
            relatedIds = listOf("c1", "c2"),
            isEngineeringSpecial = true
        ),
        ContentItem(
            id = "c6",
            title = "هوش مصنوعی در Oil & Gas: ترندهای سال ۲۰۲۶ و فرصت‌های مهندسان",
            category = "Oil & Gas",
            type = ContentType.REEL,
            level = ContentLevel.BEGINNER,
            durationText = "۳ دقیقه مشاهده",
            excerpt = "نگاهی سریع به پیش‌بینی عیب تجهیزات دوار (Predictive Maintenance) و بهینه‌سازی لوپ‌های تفکیک‌گر نفت با مدل‌های ترکیبی Physics-Informed ML.",
            fullContent = """
صنعت نفت و گاز به سرعت به سمت دیجیتالی شدن حرکت می‌کند. یادگیری ماشین بر پایه فیزیک (PINN) و ایجنت‌های هوشمند صنعتی هم‌اکنون در پالایشگاه‌ها و سکوهای دریایی برای کاهش توقف‌های ناخواسته (Unplanned Shutdowns) استفاده می‌شوند.
            """.trimIndent(),
            publishDate = "۱۴۰۳/۰۶/۱۰",
            instagramReelUrl = "https://instagram.com/reel/oil_gas_ai",
            isEngineeringSpecial = true
        ),
        ContentItem(
            id = "c7",
            title = "اصول تنظیم کنترل‌کننده‌های صنعتی PID با یادگیری تقویتی (RL)",
            category = "Control",
            type = ContentType.TUTORIAL,
            level = ContentLevel.ADVANCED,
            durationText = "۹ دقیقه مطالعه",
            excerpt = "چگونه روش‌های کلاسیک زیگلر-نیکولز را با الگوریتم‌های مدرن هوش مصنوعی برای کنترل لوپ‌های غیرخطی مقایسه کنیم؟",
            fullContent = """
در سیستم‌های فرآیندی با تاخیر زمانی مرده (Dead Time) و غیرخطی بودن بالا، روش‌های سنتی تنظیم کنترل‌کننده PID کارایی خود را از دست می‌دهند. با مدل‌سازی دیجیتال تویین و اعمال الگوریتم یادگیری تقویتی، می‌توان ضرایب بهینه را بدون ریسک در کارخانه واقعی استخراج کرد.
            """.trimIndent(),
            publishDate = "۱۴۰۳/۰۶/۲۵",
            relatedIds = listOf("c2", "c6"),
            isEngineeringSpecial = true
        )
    )

    val projects: List<ProjectItem> = listOf(
        ProjectItem(
            id = "p1",
            title = "AI Engineering Document Reviewer",
            status = ProjectStatus.BUILDING,
            description = "سیستم خودکار بررسی نقشه‌های مهندسی و انطباق سنجی دیتاشیت تجهیزات برق و مکانیک با استانداردهای صنعتی.",
            problem = "مهندسان صدها ساعت در ماه را صرف بررسی دستی اسناد فنی و مقایسه مقادیر با جداول مرجع می‌کنند که احتمال خطای انسانی در آن بسیار بالاست.",
            solution = "ایجنت هوشمندی که نقشه‌ها و دیتاشیت‌های PDF را پارس کرده و با استانداردهای مربوطه مقایسه و مغایرت‌ها را همراه با استناد به شماره بند گزارش می‌کند.",
            architecture = "Vision OCR + LangChain Multi-Agent Architecture + Chroma Vector Store + Streamlit / Jetpack Compose Frontend.",
            technologies = listOf("Python", "LangChain", "Gemini 3.5 Flash", "ChromaDB", "FastAPI"),
            lessons = "در پروژه‌های مهندسی، دقت ۱۰۰٪ بر سرعت ارجحیت دارد؛ بنابراین باید خروجی LLM همیشه با منابع استاندارد cross-check شود.",
            githubUrl = "https://github.com/mohammad-builder/engineering-doc-reviewer",
            demoUrl = "https://demo.mohammad.app/reviewer"
        ),
        ProjectItem(
            id = "p2",
            title = "RAG Engineering Assistant",
            status = ProjectStatus.LIVE,
            description = "دستیار هوشمند جستجو و استخراج اطلاعات از ۱۰,۰۰۰ صفحه استاندارد معتبر نفت و گاز (IPS, API, NFPA).",
            problem = "پیدا کردن پاسخ برای یک مسئله خاص طراحی فرآیندی در میان حجم عظیم فایل‌های متنی استاندارد ساعت‌ها زمان می‌برد.",
            solution = "سیستم RAG هیبریدی با ترکیب Keyword Search و Semantic Vector Search که بند دقیق را به همراه فرمول‌های محاسباتی به مهندس نمایش می‌دهد.",
            architecture = "Hybrid Retrieval (BM25 + Dense Embeddings) + Re-ranking Cross-Encoder + Gemini LLM.",
            technologies = listOf("Python", "Qdrant", "Sentence-Transformers", "FastAPI", "React/Compose"),
            lessons = "تقسیم‌بندی (Chunking) هوشمندانه بر اساس شماره سرفصل‌های استاندارد به شدت کیفیت پاسخ‌ها را بالا برد.",
            githubUrl = "https://github.com/mohammad-builder/rag-engineering-assistant",
            demoUrl = "https://demo.mohammad.app/standards-rag"
        ),
        ProjectItem(
            id = "p3",
            title = "Industrial Automation Agent",
            status = ProjectStatus.BETA,
            description = "ایجنت مستقل ارتباط با تجهیزات صنعتی از طریق پروتکل Modbus TCP برای پایش سلامت موتورها و ارسال آلارم تلگرامی.",
            problem = "نیاز به نظارت شبانه‌روزی بر متغیرهای حیاتی موتورهای فشار متوسط بدون نیاز به حضور دائم اپراتور در سایت.",
            solution = "اسکریپت پس‌زمینه که داده‌های حسگرهای لرزش و حرارت را خوانده و در صورت رویت الگوهای آنومالی، تحلیل علت ریشه‌ای را در لحظه ارسال می‌کند.",
            architecture = "Modbus Client + Anomaly Detection Model + Telegram Bot Notification Gateway.",
            technologies = listOf("Python", "PyModbus", "Scikit-Learn", "Asyncio", "Docker"),
            lessons = "قابلیت اطمینان ارتباط شبکه در محیط صنعتی چالشی جدی است؛ سیستم باید قابلیت retry هوشمند و کش آفلاین داشته باشد.",
            githubUrl = "https://github.com/mohammad-builder/modbus-ai-agent"
        ),
        ProjectItem(
            id = "p4",
            title = "Content & Social Automation Hub",
            status = ProjectStatus.COMPLETED,
            description = "پایپ‌لاین کامل تولید و زمان‌بندی محتوای آموزشی برای شبکه‌های اجتماعی اینستاگرام و لینکدین محمد.",
            problem = "تولید مستمر محتوای فنی باکیفیت همزمان با کار تمام‌وقت مهندسی نیازمند زمان و انرژی بسیار بالایی است.",
            solution = "سیستمی که مقالات فنی بلاگ را به سناریوی ریلز، پست‌های اسلایدی و هوک‌های جذاب لینکدین تبدیل و خودکار پیش‌نویس می‌کند.",
            architecture = "N8N Automation + Gemini API + Notion API + Canva API Automation.",
            technologies = listOf("N8N", "Python", "Gemini API", "Notion API", "Telegram Bot"),
            lessons = "اتوماسیون محتوا زمانی ارزش دارد که هویت شخصی و صدای واقعی سازنده در آن حفظ شود؛ خروجی AI همیشه نیازمند ویرایش نهایی انسانی است.",
            githubUrl = "https://github.com/mohammad-builder/personal-brand-automation"
        )
    )

    val journeyPosts: List<JourneyItem> = listOf(
        JourneyItem(
            id = "j1",
            dayNumber = 58,
            date = "امروز — ۱۴۰۳/۰۶/۲۸",
            title = "روز ۵۸ — اولین آزمایش موفق ایجنت بررسی مدارک در پروژه واقعی",
            story = "امروز برای اولین بار توانستیم خروجی ایجنت بازبینی دیتاشیت‌ها را در یک جلسه فنی واقعی ارزیابی کنیم. ایجنت توانست یک مغایرت ظریف در متریال فلنج کابل با استاندارد کارفرما را شناسایی کند که دو مهندس در بررسی اولیه ندیده بودند!",
            lesson = "هوش مصنوعی جایگزین مهندس نمی‌شود، بلکه مثل یک همکار خستگی‌ناپذیر است که جزئیات ریز را چک می‌کند تا مهندس روی تصمیم‌گیری کلان متمرکز شود.",
            nextStep = "توسعه واسط گرافیکی تمیز برای آپلود دسته‌ای اسناد مهندسی توسط اعضای تیم.",
            category = JourneyCategory.ACHIEVEMENT
        ),
        JourneyItem(
            id = "j2",
            dayNumber = 42,
            date = "۱۶ روز پیش — ۱۴۰۳/۰۶/۱۲",
            title = "روز ۴۲ — ساخت اولین Agent واقعی و چالش Hallucination در محاسبات",
            story = "امروز بالاخره Agent توانست اولین پایپ‌لاین تحلیل خود را به پایان برساند، اما در محاسبه افت ولتاژ یک عدد تخیلی تولید کرد! متوجه شدم که نباید از LLM انتظار ماشین حساب ریاضی داشت، بلکه باید به کمک Function Calling فرمول‌های قطعی پایتون را اجرا کند.",
            lesson = "مدل‌های زبانی برای استدلال عالی هستند و کد پایتون برای محاسبات قطعی. ترکیب این دو با Function Calling کلید سیستم‌های مهندسی بدون خطاست.",
            nextStep = "جدا کردن لایه استدلال از لایه محاسبات عددی با استفاده از Tool Calling.",
            category = JourneyCategory.EXPERIMENT
        ),
        JourneyItem(
            id = "j3",
            dayNumber = 31,
            date = "۲۷ روز پیش — ۱۴۰۳/۰۶/۰۱",
            title = "روز ۳۱ — شکست در ایندکس کردن فایل‌های اسکن شده قدیمی",
            story = "تلاش کردم آرشیو نقشه‌های دهه ۸۰ را وارد وکتور دیتابیس کنم. به خاطر کیفیت پایین اسکن و نویزهای گرافیکی، خروجی OCR کاملاً به هم ریخت و پاسخ‌های نامفهوم داد.",
            lesson = "داده بی‌کیفیت مساوی با هوش مصنوعی بی‌ارزش است (Garbage In, Garbage Out). پیش‌پردازش تصویر و فیلترهای حذف نویز در اسناد فنی حیاتی‌ترین بخش کار است.",
            nextStep = "استفاده از فیلترهای OpenCV برای پاکسازی نویز قبل از فرستادن به موتور OCR.",
            category = JourneyCategory.FAILURE
        ),
        JourneyItem(
            id = "j4",
            dayNumber = 15,
            date = "۴۳ روز پیش — ۱۴۰۳/۰۵/۲۰",
            title = "روز ۱۵ — تصمیم بزرگ: تلفیق مهندسی سنتی برق با دنیای AI",
            story = "مدت‌ها بود بین کار مهندسی صنعتی و یادگیری هوش مصنوعی دچار دوگانگی بودم. احساس می‌کردم باید یکی را انتخاب کنم. تا اینکه فهمیدم بزرگترین ارزش من دقیقاً در نقطه تلاقی این دو رشته است: جایی که مهندسان هوش مصنوعی نمی‌دانند تابلو برق چطور کار می‌کند، و مهندسان برق از AI بی‌اطلاعند.",
            lesson = "مزیت رقابتی واقعی در تقاطع مهارت‌هاست، نه فقط عمیق شدن در یک تخصص منفرد.",
            nextStep = "انتشار عمومی اولین مجموعه مقالات AI × Engineering.",
            category = JourneyCategory.REFLECTION
        )
    )
}
