package com.jgfidelis.gistdemoapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.favorite_button_view_layout.view.*

/**
 * Created by jgfidelis on 22/12/17.
 */
class FavoriteButtonView: RelativeLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int, defStyleRes:Int): super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.favorite_button_view_layout, this)
    }

    fun setButtonListener(callback:View.OnClickListener) {
        btn.setOnClickListener {
            btn.isClickable = false
            callback.onClick(btn)
        }
    }

    fun setState(isFavorite: Boolean) {
        if (isFavorite) {
            image_filled.visibility = View.VISIBLE
            image_empty.visibility = View.GONE
        } else {
            image_filled.visibility = View.GONE
            image_empty.visibility = View.VISIBLE
        }
    }

    fun fadeTransition(isFavorite: Boolean) {
        var viewToShow: ImageView = if (isFavorite) image_filled else image_empty
        var viewToHide: ImageView = if (isFavorite) image_empty else image_filled

        viewToShow.alpha = 0f
        viewToShow.visibility = View.VISIBLE

        var duration = resources.getInteger(android.R.integer.config_mediumAnimTime)

        viewToShow.animate().alpha(1f).setDuration(duration.toLong()).setListener(null)
        viewToHide.animate().alpha(0f).setDuration(duration.toLong()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                viewToHide.visibility = View.GONE
                btn.isClickable = true
            }
        })
    }
}