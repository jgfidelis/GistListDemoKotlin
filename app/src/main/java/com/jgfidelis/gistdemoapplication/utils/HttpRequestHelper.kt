package com.jgfidelis.gistdemoapplication.utils

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by jgfidelis on 22/12/17.
 */

interface HttpRequestCallback {
    fun onSuccess(data:String)
    fun onFailure(status:Int)
}

class HttpRequestHelper {
    companion object {
        fun requestGistList(page:Int, callback: HttpRequestCallback) {
            val url = String.format("https://api.github.com/gists/public?page=%d", page)
            doHttpRequest(url, callback)
        }

        fun requestFileContent(url:String, callback: HttpRequestCallback) {
            doHttpRequest(url, callback)
        }

        private fun doHttpRequest(url:String, callback: HttpRequestCallback) {
            Thread({
                val connection = URL(url).openConnection() as HttpURLConnection
                if(connection.responseCode == HttpURLConnection.HTTP_OK) {
                    try {
                        val stream = BufferedInputStream(connection.inputStream)
                        val data: String = readStream(stream)
                        if (data != null) callback.onSuccess(data) else callback.onFailure(connection.responseCode)
                    } catch (e : Exception) {
                        e.printStackTrace()
                        callback.onFailure(0)
                    } finally {
                        connection.disconnect()
                    }
                } else {
                    callback.onFailure(connection.responseCode)
                }
            }).start()
        }

        private fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine { stringBuilder.append(it) }
            return stringBuilder.toString()
        }
    }
}