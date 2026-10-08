# ConnectTag Formatter Library (formatter-lib-kmp)

[![JitPack](https://jitpack.io/v/connecttag/formatter-lib-kmp.svg)](https://jitpack.io/#connecttag/formatter-lib-kmp)

مكتبة التنسيق الشامل للبيانات والأرقام والعملات لتطبيقات Kotlin Multiplatform:
- **Android** (API 24+)
- **Desktop JVM** (Windows, macOS, Linux)
- **iOS** (Arm64, SimulatorArm64)

---

## 📦 التثبيت والإضافة للمشروع (Installation via JitPack)

### 1. إضافة مستودع JitPack في `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. إضافة التبعية في `build.gradle.kts`:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            // For Kotlin Multiplatform:
            implementation("com.github.connecttag.formatter-lib-kmp:formatter-lib-kmp:1.0.0")
        }
    }
}
```

الحزمة الأساسية للمكتبة: `org.connecttag.lib.formatter.*` أو `com.platformtag.formatter.*`

---

## 🌟 الميزات والاستخدام

1. **`MoneyFormatter` & `CurrencyRegistry`:**
   - تنسيق المبالغ المالية مع العملات (YER, USD, SAR, EGP...) بدقة حسب الموقع واللغة (عربي/إنجليزي) ومواضع الرمز (`BeforeAmount`, `AfterAmount`).
2. **`DistanceFormatter`:**
   - تحويل وتنسيق المسافات التلقائي بين الأمتار والكيلومترات (مثال: `500 م` أو `3.2 كم`).
3. **`CoordinateFormatter`:**
   - تنسيق الإحداثيات الجغرافية (Latitude / Longitude) بصيغ دقيقة ومقروءة للمستخدم.
4. **`DecimalNumberFormatter` & `DigitFormatter`:**
   - تنسيق الكسور العشرية والتحويل الموحد بين الأرقام العربية والإنجليزية.
5. **`SizeFormatter`:**
   - تنسيق أحجام الملفات والذاكرة التلقائي (B, KB, MB, GB, TB).
6. **`MessageTemplateFormatter`:**
   - استبدال المتغيرات والقوالب النصية ديناميكياً.
