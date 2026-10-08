package com.platformtag.ext

public fun String?.cleanOrNull(): String? = this?.trim()?.takeIf { it.isNotEmpty() }
