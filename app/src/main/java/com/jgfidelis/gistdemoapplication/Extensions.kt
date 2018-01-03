package com.jgfidelis.gistdemoapplication

import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder

/**
 * Created by jgfidelis on 20/12/17.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun convertToJson(obj:Any):String {
    val gson = GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create()
    val json: String = gson.toJson(obj)
    return json
}

fun Fragment.willShow() {

}