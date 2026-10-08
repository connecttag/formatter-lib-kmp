package com.platformtag.formatter



object PasswordUtils {
    fun isValidPassword(password: String): Boolean {
        val passwordRegex =
            Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$")
        return passwordRegex.matches(password)
    }

    fun isStrongPassword(password: String): Boolean {
        val hasLetters = password.any { it.isLetter() }
        val hasDigits = password.any { it.isDigit() }
        val hasSymbols = password.any { !it.isLetterOrDigit() }

        return password.length >= 8 && hasLetters && hasDigits && hasSymbols
    }

    // التحقق من نوع كلمة المرور (حروف وأرقام ورموز، حروف وأرقام، إلخ)
    fun getPasswordType(password: String): String {
        val hasLetters = password.any { it.isLetter() }
        val hasDigits = password.any { it.isDigit() }
        val hasSymbols = password.any { !it.isLetterOrDigit() }

        return when {
            hasLetters && hasDigits && hasSymbols -> "حروف وأرقام ورموز"
            hasLetters && hasDigits -> "حروف وأرقام"
            hasLetters && hasSymbols -> "حروف ورموز"
            hasDigits && hasSymbols -> "أرقام ورموز"
            hasLetters -> "حروف فقط"
            hasDigits -> "أرقام فقط"
            hasSymbols -> "رموز فقط"
            else -> "غير معروف"
        }
    }


}
