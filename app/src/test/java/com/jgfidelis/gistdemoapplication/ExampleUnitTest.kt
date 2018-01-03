package com.jgfidelis.gistdemoapplication

import android.content.Context
import com.jgfidelis.gistdemoapplication.models.FileModelObject
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import com.jgfidelis.gistdemoapplication.utils.HttpRequestCallback
import com.jgfidelis.gistdemoapplication.utils.HttpRequestHelper
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testGistObjectParse() {
        val context = mock(Context::class.java)
        val string = "{\"description\":\"My description\", \"id\":\"aaaa\", \"owner\":{ \"login\":\"Marcos\", \"avatar_url\":\"url\"}, \"files\": { \"teste.java\":{ \"language\":\"Java\", \"filename\":\"teste.java\", \"raw_url\":\"url\" } } }"
        val gist = GistModelObject.parse(
                JSONObject(string),
                context
        )
        assertNotNull(gist)
        assertEquals(1, gist.files.size)
        assertEquals("teste.java", gist.title)
        assertEquals("My description", gist.description)
        assertEquals("aaaa", gist.gid)
        assertEquals("Marcos", gist.authorName)
        assertEquals("url", gist.authorUrl)
        assertEquals("teste.java", gist.files[0].fileName)
        assertEquals("Java", gist.files[0].language)
        assertEquals("url", gist.files[0].fileUrl)
    }

    @Test
    fun testGistObjectParseMultipleFiles() {
        val context = mock(Context::class.java)
        val string = "{\"description\":\"My description\", \"id\":\"aaaa\", \"owner\":{ \"login\":\"Marcos\", \"avatar_url\":\"url\"}, \"files\": { \"teste.java\":{ \"language\":\"Java\", \"filename\":\"teste.java\", \"raw_url\":\"url\" }, \"index.js\":{ \"language\":\"JavaScript\", \"filename\":\"index.js\", \"raw_url\":\"url\" }, \"MainActivity.kt\":{ \"language\":\"Kotlin\", \"filename\":\"MainActivity.kt\", \"raw_url\":\"url\" } } }"
        val gist = GistModelObject.parse(
                JSONObject(string),
                context
        )
        assertNotNull(gist)
        assertEquals(3, gist.files.size)
        assertEquals("index.js", gist.title)
        assertEquals("My description", gist.description)
        assertEquals("aaaa", gist.gid)
        assertEquals("Marcos", gist.authorName)
        assertEquals("url", gist.authorUrl)
        assertEquals("index.js", gist.files[0].fileName)
        assertEquals("JavaScript", gist.files[0].language)
        assertEquals("url", gist.files[0].fileUrl)
        assertEquals("MainActivity.kt", gist.files[1].fileName)
        assertEquals("Kotlin", gist.files[1].language)
        assertEquals("url", gist.files[1].fileUrl)
        assertEquals("teste.java", gist.files[2].fileName)
        assertEquals("Java", gist.files[2].language)
        assertEquals("url", gist.files[2].fileUrl)
    }

    @Test
    fun testFileObjectParse() {
        val context = mock(Context::class.java)
        val string = "{\"language\":\"Java\", \"filename\":\"teste.java\", \"raw_url\":\"url\"}"
        val file = FileModelObject.parse(JSONObject(string), context)
        assertNotNull(file)
        assertEquals("teste.java", file.fileName)
        assertEquals("Java", file.language)
        assertEquals("url", file.fileUrl)
    }

    @Test
    fun testGistRequest() {
        val callback = object: HttpRequestCallback {
            override fun onSuccess(data: String) {
                assertNotNull(data)
            }
            override fun onFailure(status: Int) {
                assertNotEquals(200, status)
            }
        }
        HttpRequestHelper.requestGistList(0, callback)
    }

    @Test
    fun testFileDownload() {
        val callback = object: HttpRequestCallback {
            override fun onSuccess(data: String) {
                assertNotNull(data)
            }
            override fun onFailure(status: Int) {
                assertNotEquals(200, status)
            }
        }
        val url = "https://gist.githubusercontent.com/ko1/56ab4d29e921e4500290a414bd88e0c1/raw/ca2099463bd8bbbb3bf60eaa80e8b15d28d8bcc9/brlog.trunk-test.20171224-060819"
        HttpRequestHelper.requestFileContent(url, callback)
    }
}
