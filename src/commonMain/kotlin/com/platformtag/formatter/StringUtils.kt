package com.platformtag.formatter

object StringUtils {

    // التحقق من اللغة (عربي أو إنجليزي)
    fun isArabic(text: String): Boolean {
        return text.matches(Regex("^[\\p{IsArabic}\\s]+$"))
    }

    fun isEnglish(text: String): Boolean {
        return text.matches(Regex("^[\\p{IsLatin}\\s]+$"))
    }

    // عدد الكلمات
    fun wordCount(text: String): Int {
        return text.trim().split("\\s+".toRegex()).size
    }

    // إزالة الرموز والأرقام
    fun removeSymbolsAndNumbers(text: String): String {
        return text.replace(Regex("[^\\p{L}\\s-]"), "")
    }

    // إزالة الفراغات في البداية والنهاية
    fun trimSpaces(text: String): String {
        return text.trim()
    }

    // تطبيع النص العربي (اختياري)
    fun normalizeArabic(text: String): String {
        return text
            .replace(ARABIC_DIACRITICS_REGEX, "")
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
    }


    fun replaceSpecialCharacters(text: String): String {
        return text
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
            .replace("ب", "ا") // إذا كنت تقصد استبدال "ب" بـ "ا"
            .replace("ة", "ه")
            .replace("ئ", "ى")
    }


}

private val ARABIC_DIACRITICS_REGEX = Regex("[\\u0610-\\u061A\\u064B-\\u065F\\u0670\\u06D6-\\u06ED]")
