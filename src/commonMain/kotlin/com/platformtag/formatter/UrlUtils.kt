package com.platformtag.formatter

import com.platformtag.network.HttpUrlPolicy

private const val QUERY_KEY_VALUE_SEPARATOR = "="
private const val QUERY_PARAM_SEPARATOR = "&"
private const val FRAGMENT_PREFIX = "#"
private const val PATH_PREFIX = "/"
private const val SCHEME_SUFFIX = "://"
private const val QUERY_PREFIX = "?"

fun isValidUrl(input: String): Boolean {
    return HttpUrlPolicy.isHttpUrl(input)
}

fun getDomainName(url: String): String? = UrlUtils.getDomainName(url)

fun getScheme(url: String): String? = UrlUtils.getScheme(url)

fun getPath(url: String): String? = UrlUtils.getPath(url)

fun getQueryParameters(url: String): Map<String, String>? = UrlUtils.getQueryParameters(url)

object UrlUtils {
    fun isValidUrl(url: String): Boolean {
        return HttpUrlPolicy.isHttpUrl(url)
    }

    fun getDomainName(url: String): String? {
        if (!HttpUrlPolicy.isHttpUrl(url)) return null
        val trimmed = url.trim()
        val schemeEnd = trimmed.indexOf(SCHEME_SUFFIX)
        if (schemeEnd < 0) return null
        val hostPart = trimmed.substring(schemeEnd + SCHEME_SUFFIX.length)
            .substringBefore(PATH_PREFIX)
            .substringBefore(QUERY_PREFIX)
            .substringBefore(FRAGMENT_PREFIX)
            .substringBefore(':')
            .trimEnd('.')
        return hostPart.ifEmpty { null }
    }

    fun getScheme(url: String): String? {
        val trimmed = url.trim()
        if (trimmed.isBlank()) return null
        val schemeEnd = trimmed.indexOf(SCHEME_SUFFIX)
        if (schemeEnd <= 0) return null
        return trimmed.substring(0, schemeEnd).lowercaseStable()
    }

    fun getPath(url: String): String? {
        if (!HttpUrlPolicy.isHttpUrl(url)) return null
        val trimmed = url.trim()
        val schemeEnd = trimmed.indexOf(SCHEME_SUFFIX)
        if (schemeEnd < 0) return null
        val pathStart = schemeEnd + SCHEME_SUFFIX.length
        val hostAndTail = trimmed.substring(pathStart)
        val slash = hostAndTail.indexOf(PATH_PREFIX)
        if (slash < 0) return ""
        val tail = hostAndTail.substring(slash + PATH_PREFIX.length)
        val pathAndQuery = tail.substringBefore(FRAGMENT_PREFIX)
        val path = pathAndQuery.substringBefore(QUERY_PREFIX)
        return if (path.isEmpty()) PATH_PREFIX else "$PATH_PREFIX$path"
    }

    fun getQueryParameters(url: String): Map<String, String>? {
        if (!HttpUrlPolicy.isHttpUrl(url)) return null
        val trimmed = url.trim()
        val queryStart = trimmed.indexOf(QUERY_PREFIX)
        if (queryStart < 0) return emptyMap()

        val fragmentStart = trimmed.indexOf(FRAGMENT_PREFIX, queryStart + 1)
        val query = if (fragmentStart < 0) {
            trimmed.substring(queryStart + 1)
        } else {
            trimmed.substring(queryStart + 1, fragmentStart)
        }

        if (query.isBlank()) return emptyMap()
        return query.split(QUERY_PARAM_SEPARATOR).mapNotNull { pair ->
            val separator = pair.indexOf(QUERY_KEY_VALUE_SEPARATOR)
            if (separator < 0) {
                pair.trim().takeIf { it.isNotEmpty() }?.let { key -> key to "" }
            } else {
                val key = pair.substring(0, separator).trim()
                val value = pair.substring(separator + 1).trim()
                key.takeIf { it.isNotEmpty() }?.let { keyValue -> keyValue to value }
            }
        }.toMap()
    }
}
