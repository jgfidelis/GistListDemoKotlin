package com.jgfidelis.gistdemoapplication.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.jgfidelis.gistdemoapplication.R
import org.json.JSONObject

/**
 * Created by jgfidelis on 18/12/17.
 */
class FileModelObject(language:String?, fileName:String, fileUrl:String) {
    @Expose val language:String?
    @Expose val fileName:String
    @Expose val fileUrl:String
    init {
        this.language = language
        this.fileName = fileName
        this.fileUrl = fileUrl
    }
    companion object {
        fun parse(jsonObject: JSONObject, context:Context): FileModelObject {
            var language = jsonObject.optString("language")
            if (language == "null") {
                language = context.resources.getString(R.string.not_specified)
            }
            val fileName = jsonObject.optString("filename")
            val fileUrl = jsonObject.optString("raw_url")
            return FileModelObject(language, fileName, fileUrl)
        }

        fun convertJsonToFileArray(json:String):List<FileModelObject> {
            val mylist: List<FileModelObject> = Gson().fromJson(json, Array<FileModelObject>::class.java).toList()
            return mylist
        }

        fun convertJsonToFile(json:String): FileModelObject {
            val file: FileModelObject = Gson().fromJson(json, FileModelObject::class.java)
            return file
        }
    }
}