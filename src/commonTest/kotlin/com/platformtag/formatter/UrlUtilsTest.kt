package com.platformtag.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UrlUtilsTest {
    @Test
    fun isValidUrlMatchesHttpAndHttpsOnly() {
        assertTrue(isValidUrl("https://example.com"))
        assertTrue(UrlUtils.isValidUrl("https://example.com/path"))
        assertFalse(isValidUrl("ftp://example.com"))
        assertFalse(UrlUtils.isValidUrl("not-a-url"))
    }

    @Test
    fun getDomainSchemePathAndQueryParsedFromUrl() {
        assertEquals("example.com", getDomainName("https://example.com/path?x=1"))
        assertEquals("https", getScheme("HTTPS://example.com/path?x=1"))
        assertEquals("/path", getPath("https://example.com/path?x=1"))
        assertEquals(mapOf("x" to "1"), getQueryParameters("https://example.com/path?x=1"))
    }

    @Test
    fun getQueryParametersReturnsEmptyMapWhenMissingQuery() {
        assertEquals(emptyMap(), UrlUtils.getQueryParameters("https://example.com/path"))
    }

    @Test
    fun getQueryParametersReturnsNullWhenInvalidUrl() {
        assertNull(UrlUtils.getQueryParameters("not-a-url"))
    }
}
