package com.nicomahnic.sharecsv

import android.app.Application

class ShareFileApp : Application() {
    override fun onCreate() {
        super.onCreate()
        println("NAMG: shareFileApp 1")
        AndroidShareFile.setUp(applicationContext)
        println("NAMG: shareFileApp 2")
    }

}