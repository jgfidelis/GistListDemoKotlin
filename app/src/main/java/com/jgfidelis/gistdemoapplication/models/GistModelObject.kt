package com.jgfidelis.gistdemoapplication.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.jgfidelis.gistdemoapplication.R
import org.json.JSONObject

/**
 * Created by jgfidelis on 18/12/17.
 */

class GistModelObject(gid:String, title:String, authorName:String, authorUrl:String?, files:ArrayList<FileModelObject>, description:String?) {
    @Expose val gid:String
    @Expose val title:String
    @Expose val authorName:String
    @Expose val authorUrl:String?
    @Expose val files:ArrayList<FileModelObject>
    @Expose val description:String?
    init {
        this.gid = gid
        this.title = title
        this.authorName = authorName
        this.authorUrl = authorUrl
        this.files = files
        this.description = description
    }

    companion object {
        fun parse(jsonObject: JSONObject, context: Context): GistModelObject {
            val gid = jsonObject.optString("id")
            var description = jsonObject.optString("description")
            if (description == "null") {
                description = null
            }

            val owner = jsonObject.optJSONObject("owner")
            val authorName = owner?.optString("login") ?: context.resources.getString(R.string.unknown)
            val authorUrl = owner?.optString("avatar_url")
            val fileMap = jsonObject.optJSONObject("files")
            val keys = fileMap.keys()
            val fileArray = ArrayList<FileModelObject>()
            for (key in keys) {
                val file = FileModelObject.parse(fileMap.optJSONObject(key), context)
                fileArray.add(file)
            }
            val title = fileArray[0].fileName
            return GistModelObject(gid, title, authorName, authorUrl, fileArray, description)
        }

        fun convertJsonToGistArray(json:String):List<GistModelObject> {
            val mylist: List<GistModelObject> = Gson().fromJson(json, Array<GistModelObject>::class.java).toList()
            return mylist
        }

        fun convertJsonToGist(json:String): GistModelObject {
            val gist: GistModelObject = Gson().fromJson(json, GistModelObject::class.java)
            return gist
        }
    }
}

