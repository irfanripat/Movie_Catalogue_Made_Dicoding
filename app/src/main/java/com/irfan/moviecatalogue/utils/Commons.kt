package com.irfan.moviecatalogue.utils

import android.view.View

object Commons {

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun Double?.rating() : Float {
        if (this != null)
            return this.toFloat() / 2.0f

        return 0.0f
    }
}