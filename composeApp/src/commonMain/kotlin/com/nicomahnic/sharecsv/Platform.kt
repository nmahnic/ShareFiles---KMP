package com.nicomahnic.sharecsv

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform