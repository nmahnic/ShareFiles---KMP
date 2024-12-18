package com.nicomahnic.sharecsv

import android.app.Application

class ShareFileApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidShareFile.setUp(applicationContext)
    }

}