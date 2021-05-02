package com.mvk.news.utils.common

import java.io.InputStream
import java.nio.charset.Charset

/**
 * Read input from a file
 *
 * @param charset InputStream
 * @return Read string
 */
fun InputStream.readInput(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}