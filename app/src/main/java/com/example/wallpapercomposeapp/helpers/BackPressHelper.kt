package com.example.wallpapercomposeapp.helpers

import android.content.Context
import android.content.Intent

object BackPressHelper {
    fun onBackPressing(context: Context) {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(homeIntent)
    }
}