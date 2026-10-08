package com.platformtag.formatter

object IPAddressUtils {

    fun isValidIPv4Address(ipAddress: String): Boolean {
        val parts = ipAddress.split(".")
        return parts.size == IPV4_PART_COUNT && parts.all { part ->
            part.isNotEmpty() &&
                part.length <= IPV4_MAX_PART_LENGTH &&
                part.all(Char::isDigit) &&
                part.toIntOrNull() in 0..IPV4_MAX_PART_VALUE
        }
    }

    fun isValidIPv6Address(ipAddress: String): Boolean {
        if (ipAddress.isBlank()) return false
        if (ipAddress.count { it == ':' } < IPV6_MIN_COLON_COUNT) return false

        val hasCompression = ipAddress.contains("::")
        if (hasCompression && ipAddress.indexOf("::") != ipAddress.lastIndexOf("::")) return false

        val groups = ipAddress.split(":")
        val presentGroups = groups.filter { it.isNotEmpty() }
        val maxGroups = if (hasCompression) IPV6_GROUP_COUNT - 1 else IPV6_GROUP_COUNT
        return presentGroups.isNotEmpty() &&
            presentGroups.size <= maxGroups &&
            (!hasCompression && presentGroups.size == IPV6_GROUP_COUNT || hasCompression) &&
            presentGroups.all { group ->
                group.length <= IPV6_MAX_GROUP_LENGTH && group.all { char -> char.isHexDigit() }
            }
    }

    fun isValidIPAddress(ipAddress: String): Boolean {
        return isValidIPv4Address(ipAddress) || isValidIPv6Address(ipAddress)
    }

    fun getIPAddressType(ipAddress: String): String {
        return when {
            isValidIPv4Address(ipAddress) -> "IPv4"
            isValidIPv6Address(ipAddress) -> "IPv6"
            else -> "Invalid"
        }
    }

    // تنظيف عنوان IP (إزالة المسافات الزائدة والأحرف غير الصالحة)
    fun cleanIPAddress(ipAddress: String): String {
        return ipAddress.trim().replace(Regex("[^0-9a-fA-F.:]"), "")
    }

    // الحصول على الأوكتات (الأجزاء الرقمية) لعنوان IPv4
    fun getIPv4Octets(ipAddress: String): List<Int>? {
        return if (isValidIPv4Address(ipAddress)) {
            ipAddress.split(".").map { it.toInt() }
        } else {
            null
        }
    }

    // الحصول على المجموعات السداسية لعنوان IPv6
    fun getIPv6Groups(ipAddress: String): List<String>? {
        return if (isValidIPv6Address(ipAddress)) {
            ipAddress.split(":")
        } else {
            null
        }
    }

    private fun Char.isHexDigit(): Boolean {
        return this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'
    }

    private const val IPV4_PART_COUNT = 4
    private const val IPV4_MAX_PART_LENGTH = 3
    private const val IPV4_MAX_PART_VALUE = 255
    private const val IPV6_GROUP_COUNT = 8
    private const val IPV6_MAX_GROUP_LENGTH = 4
    private const val IPV6_MIN_COLON_COUNT = 2
}
