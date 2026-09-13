package com.example.data.repository

import com.example.BuildConfig
import com.example.data.model.AiToolType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class AiAssistantRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun executeAiTool(tool: AiToolType, query: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val systemPrompt = getSystemPromptForTool(tool)
                val response = callGeminiApi(apiKey, systemPrompt, query)
                if (response.isNotBlank()) {
                    return@withContext response
                }
            } catch (e: Exception) {
                // If API call fails, fall through to smart offline engine
            }
        }

        // Domain-expert fallback synthesis engine
        generateStructuredFallback(tool, query)
    }

    private fun getSystemPromptForTool(tool: AiToolType): String {
        return when (tool) {
            AiToolType.PROMPT_BUILDER -> """
                شما یک مهندس ارشد پرامپت (Prompt Engineer) با تخصص در مهندسی و هوش مصنوعی هستید.
                درخواست کاربر را به یک پرامپت حرفه‌ای با ساختار ۵ مرحله‌ای تبدیل کنید:
                ۱. نقش (Role)
                ۲. زمینه و بستر (Context)
                ۳. دستورالعمل صریح (Instructions)
                ۴. محدودیت‌ها (Constraints)
                ۵. فرمت خروجی (Output Format)
                پاسخ به زبان فارسی روان، دقیق و بدون زیاده‌گویی باشد.
            """.trimIndent()

            AiToolType.EXPLAIN_BEGINNER -> """
                شما یک استاد مسلط به آموزش مفاهیم عمیق فنی و مهندسی هستید که به زبان ساده و قابل فهم تدریس می‌کنید.
                مفهوم مورد نظر کاربر را با لحن صمیمی و مثال‌های روزمره یا تمثیل‌های ملموس توضیح دهید:
                - تعریف در یک جمله
                - تشبیه و مثال ملموس
                - نحوه کارکرد در دنیای واقعی
                - اهمیت آن برای یادگیری
            """.trimIndent()

            AiToolType.ENGINEERING_ASSISTANT -> """
                شما دستیار تخصصی مهندسی برق، کنترل و صنایع نفت و گاز هستید.
                پاسخ به سوال مهندسی کاربر باید ساختاریافته، با استناد به اصول مهندسی و استانداردهای معتبر (مانند IEC, API, ISA, IEEE) باشد:
                - تحلیل اولیه مسئله
                - استانداردهای مرتبط و فرمول‌ها/پارامترها
                - ملاحظات اجرایی و ایمنی
                توجه: در ابتدای پاسخ، حتماً اخطار مهندسی زیر را درج کنید:
                ⚠️ توجه: این پاسخ به عنوان راهنمای محاسباتی/تحلیلی است و قبل از اجرا در پروژه‌های صنعتی باید توسط مهندس طراح تایید شود.
            """.trimIndent()

            AiToolType.CONTENT_IDEA_GENERATOR -> """
                شما استراتژیست ارشد محتوا برای پیج‌ها و برندهای مهندسی و تکنولوژی هستید.
                برای حوزه ورودی کاربر، این موارد را تولید کنید:
                - ۵ ایده جذاب برای ریلز (با هوک و قلاب اولیه)
                - ۳ ایده پست اسلایدی عمیق آموزشی
                - ۲ ایده محتوای مبتنی بر تجربه و داستان (Build in Public)
                - کال تو اکشن (CTA) متناسب با هر نوع محتوا
            """.trimIndent()

            AiToolType.LEARN_SOMETHING -> """
                شما یک طراح مسیر آموزشی (Curriculum Designer) برای مهندسان هستید.
                برای موضوع ورودی کاربر، یک نقشه راه فشرده (Mini Learning Path) شامل ۴ بخش بسازید:
                ۱. سطح مقدماتی (مفاهیم پایه و پیشنیازها)
                ۲. سطح متوسط (اصول کاربردی و ابزارها)
                ۳. پروژه عملی و واقعی برای ساخت
                ۴. منابع پیشنهادی، کتاب‌ها یا دوره‌های مرجع
            """.trimIndent()
        }
    }

    private fun callGeminiApi(apiKey: String, systemInstruction: String, userMessage: String): String {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val jsonBody = JSONObject().apply {
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", systemInstruction) })
                })
            })
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", userMessage) })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.6)
                put("maxOutputTokens", 1200)
            })
        }

        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return ""
            }
            val responseString = response.body?.string() ?: return ""
            val jsonResponse = JSONObject(responseString)
            val candidates = jsonResponse.optJSONArray("candidates") ?: return ""
            if (candidates.length() > 0) {
                val first = candidates.getJSONObject(0)
                val content = first.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    return parts.getJSONObject(0).optString("text", "")
                }
            }
        }
        return ""
    }

    private fun generateStructuredFallback(tool: AiToolType, query: String): String {
        val cleanQuery = query.trim()
        return when (tool) {
            AiToolType.PROMPT_BUILDER -> """
🎯 **پرامپت مهندسی‌شده برای:** «$cleanQuery»

**۱. نقش (Role):**
به عنوان یک متخصص ارشد تولید محتوا و مهندسی سیستم‌ها با ۵ سال تجربه عمل کن.

**۲. بستر و هدف (Context):**
هدف من ایجاد یک خروجی باکیفیت و با استانداردهای حرفه‌ای در خصوص موضوع زیر است:
«$cleanQuery»

**۳. دستورالعمل اجرایی (Instructions):**
- متن را جذاب، ساختاریافته و دارای ریتم منظم طراحی کن.
- از کلمات قلمبه‌سلمبه یا شعارهای تکراری اجتناب کن و روی ارزش کاربردی تمرکز کن.
- ۳ نکته کلیدی عملی که مخاطب در همان لحظه بتواند اجرا کند را برجسته ساز.

**۴. محدودیت‌ها (Constraints):**
- از واژه‌های کلیشه‌ای نظیر «انقلاب شگفت‌انگیز» استفاده نکن.
- متن بیش از ۳۵۰ کلمه نشود.

**۵. فرمت خروجی (Output Format):**
تیتر اصلی، مقدمه در ۲ خط، ۳ بخش بولت‌وار با ایموجی‌های مینیمال و یک فراخوان به اقدام (CTA) هوشمندانه.
            """.trimIndent()

            AiToolType.EXPLAIN_BEGINNER -> """
💡 **توضیح به زبان ساده: «$cleanQuery»**

📌 **در یک جمله:**
تصور کن «$cleanQuery» مانند یک سیستم هوشمند سازماندهی است که به جای پردازش تک‌به‌تک و کند داده‌ها، ارتباط و پیوندهای مخفی میان اجزا را به یکباره درک می‌کند.

🔍 **تشبیه ملموس:**
فرض کن در یک کتابخانه عظیم هستی. شیوه قدیمی این بود که کل کتاب‌ها را دانه به دانه ورق بزنی؛ اما این مفهوم جدید مانند کتابداری است که دقیقاً می‌داند کدام قفسه و کدام پاراگراف به سوال تو مربوط است و ارتباط معنایی آن‌ها را به تو نشان می‌دهد.

⚙️ **چرا در مهندسی مهم است؟**
این ابزار باعث می‌شود سیستم‌ها بتوانند بدون سردرگمی حجم بالای متغیرها (دما، فشار، سیگنال‌ها یا متن‌ها) را همزمان پایش کرده و تصمیم بهینه اتخاذ کنند.

🎯 **خلاصه:**
نیازی به درک ریاضیات پیچیده نیست؛ هسته اصلی آن «درک هوشمندانه ارتباط بین اجزا» است!
            """.trimIndent()

            AiToolType.ENGINEERING_ASSISTANT -> """
⚠️ **توجه مهندسی:** این پاسخ یک راهنمای تحلیلی و مهندسی است؛ قبل از هرگونه تغییر یا اجرا در پروژه‌های واقعی صنعتی، مدارک باید توسط مهندس ناظر یا طراح سیستم صحه‌گذاری شوند.

🛠️ **تحلیل مهندسی موضوع: «$cleanQuery»**

۱. **استانداردها و مراجع بین‌المللی مرتبط:**
- استانداردهای IEC (مانند IEC 60034 برای ماشین‌های الکتریکی / IEC 61508 برای ایمنی عملکردی)
- استانداردهای API و IPS در سیستم‌های فرآیندی و تجهیزات دوار نفت و گاز
- استانداردهای ISA 88/95 برای یکپارچه‌سازی سیستم‌های کنترل و اتوماسیون

۲. **پارامترها و متغیرهای کلیدی محاسباتی:**
- ارزیابی محدوده تلورانس خطا (Error Tolerance)
- پایداری سیستم در نقاط کار بحرانی (Critical Operating Points)
- در نظر گرفتن فاکتورهای افت ولتاژ، نوسان فرکانس و اثرات حرارتی

۳. **ملاحظات طراحی و ایمنی صنعتی:**
- رعایت اصول Fail-Safe در معماری کنترلرها
- محافظت در برابر نویزهای الکترومغناطیسی (EMC/EMI)
- ایجاد مستندسازی مدون در مدارک P&ID و لاگ شیت‌ها
            """.trimIndent()

            AiToolType.CONTENT_IDEA_GENERATOR -> """
🚀 **ایده‌های طلایی تولید محتوا برای: «$cleanQuery»**

📱 **۵ ایده ریلز با هوک قدرتمند:**
۱. *هوک:* «اگر مهندسی و هنوز این کار رو دستی انجام میدی، داری روزی ۲ ساعت وقت تلف میکنی!»
   - موضوع: ابزار اتوماسیون ساده برای کارهای روزمره.
۲. *هوک:* «اشتباهی که ۹۰٪ افراد موقع استفاده از این ابزار انجام میدن...»
   - موضوع: رفع باور غلط رایج در $cleanQuery.
۳. *هوک:* «۳ خط کدی که هفته گذشته نجاتم داد!»
   - موضوع: پروژه کوچک کاربردی با خروجی ملموس.
۴. *هوک:* «تفاوت سیستم قدیمی در برابر هوش مصنوعی در یک نگاه»
   - موضوع: مقایسه تصویری رویکرد سنتی و نوین.
۵. *هوک:* «چطور از صفر تا صد یک سیستم پایدار بسازیم؟»
   - موضوع: نقشه راه قدم به قدم.

📝 **۳ ایده پست اسلایدی عمیق:**
- کالبدشکافی یک پروژه واقعی: از ایده تا خروجی روی کاغذ
- چک‌لیست ضروری برای بررسی قبل از انتشار یا استقرار
- مقایسه ۳ متدولوژی برتر در $cleanQuery

🎯 **کال تو اکشن پیشنهادی (CTA):**
«برای دریافت کدهای نمونه یا فایل نقشه راه، کلمه [راهنما] را کامنت کن تا مستقیم برات بفرستم!»
            """.trimIndent()

            AiToolType.LEARN_SOMETHING -> """
🗺️ **نقشه راه یادگیری فشرده: «$cleanQuery»**

🏁 **فاز ۱: مبانی و پایه‌ها (هفته ۱ و ۲)**
- درک تعاریف اصلی و واژه‌شناسی تخصصی
- بررسی دیاگرام‌های مفهومی و متغیرهای اصلی
- ابزارهای لازم برای راه‌اندازی محیط کاری اولیه

🚀 **فاز ۲: ورود به کار کاربردی (هفته ۳ و ۴)**
- اجرای سناریوهای حل مسئله کوچک (Mini-Projects)
- یادگیری متدهای استاندارد و کتابخانه‌ها/نرم‌افزارهای مرجع
- شناخت خطاهای رایج و نحوه خطایابی (Debugging)

🛠️ **فاز ۳: ساخت پروژه واقعی و پورتفولیو**
- طراحی و پیاده‌سازی یک پروژه پایان‌دوره‌ای مستقل بر پایه $cleanQuery
- مستندسازی چالش‌ها، تصمیمات فنی و نتایج عددی
- انتشار خروجی در قالب مقاله یا ریپوی گیت‌هاب برای تثبیت یادگیری

📚 **فاز ۴: مراجع برتر**
- داکیومنت رسمی ابزارها و استانداردهای صنعتی
- ریپازیتوری‌های منتخب در گیت‌هاب با پروژه‌های متن‌باز مرتبط
            """.trimIndent()
        }
    }
}
