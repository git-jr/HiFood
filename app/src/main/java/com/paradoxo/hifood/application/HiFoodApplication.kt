package com.paradoxo.hifood.application

import android.app.Application
import com.google.android.material.color.DynamicColors

class HiFoodApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

//        val dynamicColorsOptions = DynamicColorsOptions.Builder()
//            .setThemeOverlay(R.style.AppTheme_Overlay)
//            .build()
//
//        DynamicColors.applyToActivitiesIfAvailable(
//            this, dynamicColorsOptions
//        )

    }
}