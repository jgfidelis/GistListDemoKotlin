package com.jgfidelis.gistdemoapplication

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.jgfidelis.gistdemoapplication.adapters.RecyclerCustomAdapter
import com.jgfidelis.gistdemoapplication.models.FileModelObject
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import com.jgfidelis.gistdemoapplication.utils.FavoritesManager
import com.jgfidelis.gistdemoapplication.utils.HttpRequestCallback
import com.jgfidelis.gistdemoapplication.utils.HttpRequestHelper
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    companion object {
        @BeforeClass @JvmStatic fun setup() {
            val context = InstrumentationRegistry.getTargetContext()
            val gist = GistModelObject("id_remove", "Title", "Pofesso Luxa", "https://avatars3.githubusercontent.com/u/9558?v=4", ArrayList<FileModelObject>(), "description")
            FavoritesManager.getInstance(context).saveFavorite(gist)
            val savedGist = FavoritesManager.getInstance(context).getFavoritesList()[0]
            assertNotNull(savedGist)
            assertEquals(0, savedGist.files.size)
            assertEquals(gist.title, savedGist.title)
            assertEquals(gist.description, savedGist.description)
            assertEquals(gist.gid, savedGist.gid)
            assertEquals(gist.authorName, savedGist.authorName)
        }
    }

    @Test
    fun testGistNoOwner() {
        val context = InstrumentationRegistry.getTargetContext()
        val string = "{\"description\":\"My description\", \"id\":\"aaaa\", \"files\": { \"teste.java\":{ \"language\":\"Java\", \"filename\":\"teste.java\", \"raw_url\":\"url\" } } }"
        val gist = GistModelObject.parse(
                JSONObject(string),
                context
        )
        assertNotNull(gist)
        assertEquals(1, gist.files.size)
        assertEquals("teste.java", gist.title)
        assertEquals("My description", gist.description)
        assertEquals("aaaa", gist.gid)
        assertEquals(context.getString(R.string.unknown), gist.authorName)
        assertEquals("teste.java", gist.files[0].fileName)
        assertEquals("Java", gist.files[0].language)
        assertEquals("url", gist.files[0].fileUrl)
    }

    @Test
    fun testFileObjectNoLanguage() {
        val context = InstrumentationRegistry.getTargetContext()
        val string = "{\"language\":\"null\", \"filename\":\"teste.java\", \"raw_url\":\"url\"}"
        val file = FileModelObject.parse(JSONObject(string), context)
        assertNotNull(file)
        assertEquals("teste.java", file.fileName)
        assertEquals(context.getString(R.string.not_specified), file.language)
        assertEquals("url", file.fileUrl)
    }

    @Test
    fun testLoadingFromList() {
        val context = InstrumentationRegistry.getTargetContext()
        val gist = GistModelObject("id_remove", "Title", "Pofesso Luxa", "https://avatars3.githubusercontent.com/u/9558?v=4", ArrayList<FileModelObject>(), "description")

        val isFavorite = FavoritesManager.getInstance(context).isGistFavorite(gist.gid)
        assert(isFavorite)

        val savedGist = FavoritesManager.getInstance(context).getFavoritesList()[0]
        assertNotNull(savedGist)
        assertEquals(0, savedGist.files.size)
        assertEquals(gist.title, savedGist.title)
        assertEquals(gist.description, savedGist.description)
        assertEquals(gist.gid, savedGist.gid)
        assertEquals(gist.authorName, savedGist.authorName)
        FavoritesManager.getInstance(context).removeFavorite(gist.gid)
        val savedList = FavoritesManager.getInstance(context).getFavoritesList()
        assertNotNull(savedList)
        assertEquals(0, savedList.size)
    }

    @Test
    fun testSaveAndRemoveFavorite() {
        val context = InstrumentationRegistry.getTargetContext()
        val gist = GistModelObject("myid", "Title", "Marcos", "https://avatars3.githubusercontent.com/u/9558?v=4", ArrayList<FileModelObject>(), "description")
        FavoritesManager.getInstance(context).saveFavorite(gist)

        val isFavorite = FavoritesManager.getInstance(context).isGistFavorite(gist.gid)
        assert(isFavorite)

        val savedGist = FavoritesManager.getInstance(context).getFavoritesList()[0]
        assertNotNull(savedGist)
        assertEquals(0, savedGist.files.size)
        assertEquals(gist.title, savedGist.title)
        assertEquals(gist.description, savedGist.description)
        assertEquals(gist.gid, savedGist.gid)
        assertEquals(gist.authorName, savedGist.authorName)
        FavoritesManager.getInstance(context).removeFavorite(gist.gid)
        val savedList = FavoritesManager.getInstance(context).getFavoritesList()
        assertNotNull(savedList)
        assertEquals(0, savedList.size)
    }

    @Test
    fun testGistRequestAndParsing() {
        val callback = object: HttpRequestCallback {
            override fun onSuccess(data: String) {
                assertNotNull(data)
                val array = GistModelObject.convertJsonToGistArray(data)
                assert(array.size > 0)
                assert(array[0].files.size > 0)
            }
            override fun onFailure(status: Int) {
                assertNotEquals(200, status)
            }
        }
        HttpRequestHelper.requestGistList(0, callback)
    }

    @Test
    fun testCustomRecyclerUpdate() {
        val adapter = RecyclerCustomAdapter(ArrayList<GistModelObject>(), 0, false)
        assertNotNull(adapter)
        val callback = object: HttpRequestCallback {
            override fun onSuccess(data: String) {
                assertNotNull(data)
                val array = GistModelObject.convertJsonToGistArray(data)
                assert(array.size > 0)
                assert(array[0].files.size > 0)
                adapter.updateList(ArrayList(array))
                assertEquals(array.size, adapter.itemCount)
            }
            override fun onFailure(status: Int) {
                assertNotEquals(200, status)
            }
        }
        HttpRequestHelper.requestGistList(0, callback)
    }


}
